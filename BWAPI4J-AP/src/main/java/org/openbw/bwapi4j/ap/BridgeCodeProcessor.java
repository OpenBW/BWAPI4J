package org.openbw.bwapi4j.ap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"org.openbw.bwapi4j.ap.NativeClass"})
public class BridgeCodeProcessor extends AbstractProcessor {

  private STGroupFile javaTemplate;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    javaTemplate = new STGroupFile("java_templates.stg");
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.isEmpty()) {
      return false;
    }

    roundEnv
        .getElementsAnnotatedWith(NativeClass.class)
        .forEach(
            entity -> {
              TypeElement typeElement = (TypeElement) entity;
              Name className = typeElement.getQualifiedName();
              Name packageName =
                  ((QualifiedNameable) typeElement.getEnclosingElement()).getQualifiedName();

              String bridgeClassName = toBridgeName(typeElement);
              String fqBridgeClassName = toFQBridgeClassName(packageName, typeElement);
              BridgeModel bridgeModel =
                  new BridgeModel(
                      packageName, className, typeElement.getSimpleName(), bridgeClassName);
              bridgeModel.setAssignments(
                  typeElement
                      .getEnclosedElements()
                      .stream()
                      .filter(e -> e.getAnnotation(Native.class) != null)
                      .sorted(Comparator.comparing(a -> a.getSimpleName().toString()))
                      .map(
                          e -> {
                            TypeElement elementType = asTypeElement(e.asType());
                            if (elementType != null && elementType.getKind() != ElementKind.ENUM
                                && elementType.getAnnotation(NativeClass.class) != null) {
                              return mapDelegateUpdate(bridgeModel, e.getSimpleName(), packageName,
                                  elementType);
                            }
                            return new Assignment(e.getSimpleName(), valueFrom(e.asType()));
                          })
                      .collect(Collectors.toList()));

              ST template = javaTemplate.getInstanceOf("entry");
              template.add("model", bridgeModel);

              try (PrintWriter out =
                  new PrintWriter(
                      processingEnv.getFiler().createSourceFile(fqBridgeClassName).openWriter())) {
                out.print(template.render());
              } catch (IOException e) {
                throw new UncheckedIOException(e);
              }
            });
    return true;
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
            .filter(e -> e.getAnnotation(Native.class) != null)
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

    NativeLookup nativeDeclaration = typeElement.getAnnotation(NativeLookup.class);
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

  private Assignment mapDelegateUpdate(BridgeModel bridgeModel, Name name,
      Name packageName, TypeElement elementType) {
    Delegate delegate = new Delegate(toFQBridgeClassName(packageName, elementType),
        toBridgeName(elementType));
    bridgeModel.addDelegate(delegate);
    return new Assignment(
        name, new DelegateAssignment(delegate));
  }
}
