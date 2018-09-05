package org.openbw.bwapi4j.ap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Comparator;
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

  private Assignments parseAssignments(
      Set<? extends Element> allNativeClasses,
      TypeElement typeElement,
      Name packageName,
      BridgeModel bridgeModel) {
    Assignments assignments = new Assignments();
    typeElement
        .getEnclosedElements()
        .stream()
        .filter(e -> e.getAnnotation(BridgeValue.class) != null)
        .sorted(Comparator.comparing(a -> a.getSimpleName().toString().toLowerCase()))
        .forEach(
            e -> {
              BridgeValue aBridgeValue = e.getAnnotation(BridgeValue.class);
              String accessor;
              if (aBridgeValue.accessor().isEmpty()) {
                accessor = null;
              } else {
                accessor = aBridgeValue.accessor();
              }

              Named aNamed = e.getAnnotation(Named.class);

              String namedIndex = null;
              if (aNamed != null) {
                namedIndex = aNamed.name();
              } else if (aBridgeValue.initializeOnly()) {
                throw new IllegalStateException(
                    "Field "
                        + e.getSimpleName()
                        + " is initializeOnly, it must also be marked @Named!");
              }

              TypeElement elementType = asTypeElement(e.asType());
              if (isDelegate(elementType)) {
                if (aNamed != null) {
                  throw new IllegalStateException(
                      "Field "
                          + e.getSimpleName()
                          + " is a delegate, a named index might not be possible");
                }
                Delegate byDelegate =
                    mapByDelegate(bridgeModel, packageName, elementType, allNativeClasses);
                assignments.addDelegatedAssignment(new Assignment(e.getSimpleName(), byDelegate));
                return;
              }
              String indirection;
              if (aBridgeValue.indirection().isEmpty()) {
                indirection = null;
              } else {
                indirection = aBridgeValue.indirection();
              }
              Reset aReset = e.getAnnotation(Reset.class);
              if (aReset != null) {
                assignments.addResetAssignment(
                    new Assignment(
                        e.getSimpleName(), new RValue(aReset.value()), null, null));
              }
              RValue rValue = valueFrom(e.asType());
              if (namedIndex != null) {
                int index = assignments.namedFieldIndex;
                assignments.namedFieldIndex += rValue.getDataAmount();
                assignments.addNamedAssignment(
                    new NamedAssignment(
                        e.getSimpleName(),
                        rValue,
                        accessor,
                        indirection,
                        aBridgeValue.initializeOnly(),
                        namedIndex,
                        index));
                return;
              }
              assignments.addAssignment(
                  new Assignment(
                      e.getSimpleName(),
                      rValue,
                      accessor,
                      indirection));
            });
    return assignments;
  }

  private boolean isDelegate(TypeElement elementType) {
    return elementType != null
        && elementType.getKind() != ElementKind.ENUM
        && elementType.getAnnotation(NativeClass.class) != null
        && elementType.getAnnotation(LookedUp.class) == null;
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
                        .map(this::valueFrom)
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

    LookedUp nativeDeclaration = typeElement.getAnnotation(LookedUp.class);
    if (nativeDeclaration != null && !nativeDeclaration.method().isEmpty()) {
      return new RValue(
          new BWMappedValue(nativeDeclaration.method(), typeElement.getQualifiedName()));
    }

    if (typeElement.getKind() == ElementKind.ENUM) {
      return new RValue(new EnumValue(typeElement.getQualifiedName()));
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

  private Delegate mapByDelegate(
      BridgeModel bridgeModel,
      Name packageName,
      TypeElement elementType,
      Set<? extends Element> allNativeClasses) {
    List<Assignments> delegatedAssignments =
        allNativeClasses
            .stream()
            .filter(
                it ->
                    it.getAnnotation(NativeClass.class)
                        .parentName()
                        .equals(bridgeModel.getNativeClassName()))
            .map(it -> parseAssignments(allNativeClasses, (TypeElement) it, packageName, null))
            .collect(Collectors.toList());
    if (delegatedAssignments.size() != 1) {
      throw new IllegalStateException("Invalid delegate for " + elementType);
    }

    Delegate delegate =
        new Delegate(
            toFQBridgeClassName(packageName, elementType),
            toBridgeName(elementType),
            delegatedAssignments.get(0));
    bridgeModel.addDelegate(delegate);
    return delegate;
  }
}
