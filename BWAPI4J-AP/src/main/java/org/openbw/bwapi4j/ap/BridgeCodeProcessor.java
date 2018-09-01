package org.openbw.bwapi4j.ap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.StandardLocation;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"org.openbw.bwapi4j.ap.NativeClass"})
public class BridgeCodeProcessor extends AbstractProcessor {

  private STGroupFile javaTemplate;
  private STGroupFile cppTemplate;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    javaTemplate = new STGroupFile("java_templates.stg");
    cppTemplate = new STGroupFile("c++_templates.stg");
    cppTemplate.registerRenderer(
        Name.class,
        new StringRenderer() {
          @Override
          public String toString(Object o, String formatString, Locale locale) {
            return super.toString(o.toString(), formatString, locale);
          }
        });
    cppTemplate.registerRenderer(String.class, new StringRenderer());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.isEmpty()) {
      return false;
    }

    Set<? extends Element> allNativeClasses = roundEnv.getElementsAnnotatedWith(NativeClass.class);

    allNativeClasses.forEach(
        entity -> {
          TypeElement typeElement = (TypeElement) entity;
          Name className = typeElement.getQualifiedName();
          Name packageName =
              ((QualifiedNameable) typeElement.getEnclosingElement()).getQualifiedName();

          String bridgeClassName = toBridgeName(typeElement);
          String fqBridgeClassName = toFQBridgeClassName(packageName, typeElement);
          NativeClass nativeClass = entity.getAnnotation(NativeClass.class);
          BridgeModel bridgeModel =
              new BridgeModel(
                  packageName,
                  className,
                  typeElement.getSimpleName(),
                  bridgeClassName,
                  nativeClass.name(),
                  nativeClass.parentName());
          bridgeModel.setAssignments(
              parseAssignments(allNativeClasses, typeElement, packageName, bridgeModel));

          try (PrintWriter out =
              new PrintWriter(
                  processingEnv.getFiler().createSourceFile(fqBridgeClassName).openWriter())) {
            ST template = javaTemplate.getInstanceOf("entry");
            template.add("model", bridgeModel);
            out.print(template.render());
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }

          if (!nativeClass.name().isEmpty()) {
            try (PrintWriter out =
                new PrintWriter(
                    processingEnv
                        .getFiler()
                        .createResource(
                            StandardLocation.SOURCE_OUTPUT,
                            "",
                            fqBridgeClassName.replace(".", "_") + ".hpp")
                        .openWriter())) {
              ST template = cppTemplate.getInstanceOf("entry");
              template.add("model", bridgeModel);
              out.print(template.render());
            } catch (IOException e) {
              throw new UncheckedIOException(e);
            }
          }
        });
    return true;
  }

  private List<Assignment> parseAssignments(
      Set<? extends Element> allNativeClasses,
      TypeElement typeElement,
      Name packageName,
      BridgeModel bridgeModel) {
    return typeElement
        .getEnclosedElements()
        .stream()
        .filter(e -> e.getAnnotation(BridgeValue.class) != null)
        .sorted(
            (a, b) -> {
              if (a.getAnnotation(Named.class) != null && b.getAnnotation(Named.class) == null) {
                return -1;
              }
              if (b.getAnnotation(Named.class) != null && a.getAnnotation(Named.class) == null) {
                return 1;
              }
              return a.getSimpleName()
                  .toString()
                  .toLowerCase()
                  .compareTo(b.getSimpleName().toString().toLowerCase());
            })
        .map(
            e -> {
              TypeElement elementType = asTypeElement(e.asType());
              if (elementType != null
                  && elementType.getKind() != ElementKind.ENUM
                  && elementType.getAnnotation(NativeClass.class) != null
                  && elementType.getAnnotation(LookedUp.class) == null) {
                return mapDelegateUpdate(
                    bridgeModel, e.getSimpleName(), packageName, elementType, allNativeClasses);
              }
              BridgeValue aBridgeValue = e.getAnnotation(BridgeValue.class);
              String accessor;
              if (aBridgeValue.accessor().isEmpty()) {
                accessor = null;
              } else {
                accessor = aBridgeValue.accessor();
              }
              String indirection;
              if (aBridgeValue.indirection().isEmpty()) {
                indirection = null;
              } else {
                indirection = aBridgeValue.indirection();
              }
              Named aNamed = e.getAnnotation(Named.class);
              String namedIndex = null;
              if (aNamed != null) {
                bridgeModel.addNamedField(aNamed.name());
                namedIndex = aNamed.name();
              } else if (aBridgeValue.initializeOnly()) {
                throw new IllegalStateException(
                    "Field "
                        + ((Element) e).getSimpleName()
                        + " is initializeOnly, it must also be marked @Named!");
              }
              Reset aReset = e.getAnnotation(Reset.class);
              if (aReset != null) {
                bridgeModel.addResetAssignment(
                    new Assignment(
                        e.getSimpleName(),
                        new RValue(aReset.value()),
                        null,
                        null,
                        false,
                        null));
              }
              return new Assignment(
                  e.getSimpleName(),
                  valueFrom(e.asType()),
                  accessor,
                  indirection,
                  aBridgeValue.initializeOnly(),
                  namedIndex);
            })
        .collect(Collectors.toList());
  }

  private TypeElement asTypeElement(TypeMirror typeMirror) {
    return (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror);
  }

  private String toFQBridgeClassName(Name packageName, TypeElement bridgeClassName) {
    return packageName + "." + toBridgeName(bridgeClassName);
  }

  private String toBridgeName(TypeElement typeElement) {
    return typeElement.getSimpleName() + "Bridge";
  }

  private NewObjectValue toNewObjectValue(TypeElement typeElement) {
    List<? extends Element> elementsToSet =
        typeElement
            .getEnclosedElements()
            .stream()
            .filter(e -> e.getAnnotation(BridgeValue.class) != null)
            .collect(Collectors.toList());
    List<RValue> constructorArguments =
        elementsToSet
            .stream()
            .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
            .findFirst()
            .map(
                c ->
                    ((ExecutableElement) c)
                        .getParameters()
                        .stream()
                        .map(Element::asType)
                        .map(t -> valueFrom(t))
                        .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    return new NewObjectValue(
        typeElement.getQualifiedName(),
        typeElement.getSimpleName(),
        constructorArguments,
        typeElement.getEnclosingElement() instanceof TypeElement);
  }

  private RValue valueFrom(TypeMirror typeMirror) {
    TypeElement typeElement = asTypeElement(typeMirror);
    if (typeElement == null || typeMirror.toString().startsWith("java.lang")) {
      String type;
      if (typeElement == null) {
        type = typeMirror.toString();
      } else {
        switch (typeMirror.toString()) {
          case "java.lang.Integer":
            type = "int";
            break;
          default:
            throw new IllegalStateException("Unable to handle type " + typeMirror.toString());
        }
      }
      return new RValue(new PrimitiveValue(type));
    }
    if (typeElement.getKind() == ElementKind.ENUM) {
      return new RValue(new EnumValue(typeElement.getQualifiedName()));
    }

    LookedUp nativeDeclaration = typeElement.getAnnotation(LookedUp.class);
    if (nativeDeclaration != null && !nativeDeclaration.method().equals("")) {
      return new RValue(
          new BWMappedValue(nativeDeclaration.method(), typeElement.getQualifiedName()));
    }

    if (typeElement
        .getInterfaces()
        .stream()
        .anyMatch(i -> i.toString().startsWith("java.util.Collection"))) {
      DeclaredType declaredType = (DeclaredType) typeMirror;
      return new RValue(new ListValue(valueFrom(declaredType.getTypeArguments().get(0))));
    }

    NewObjectValue newObjectValue = toNewObjectValue(typeElement);
    return new RValue(newObjectValue);
  }

  private Assignment mapDelegateUpdate(
      BridgeModel bridgeModel,
      Name name,
      Name packageName,
      TypeElement elementType,
      Set<? extends Element> allNativeClasses) {
    List<Assignment> delegatedAssignments =
        allNativeClasses
            .stream()
            .filter(
                it ->
                    it.getAnnotation(NativeClass.class)
                        .parentName()
                        .equals(bridgeModel.getNativeClassName()))
            .flatMap(
                it ->
                    parseAssignments(allNativeClasses, (TypeElement) it, packageName, bridgeModel)
                        .stream())
            .collect(Collectors.toList());
    Delegate delegate =
        new Delegate(
            toFQBridgeClassName(packageName, elementType),
            toBridgeName(elementType),
            delegatedAssignments);
    bridgeModel.addDelegate(delegate);
    return new Assignment(name, new DelegateAssignment(delegate));
  }
}
