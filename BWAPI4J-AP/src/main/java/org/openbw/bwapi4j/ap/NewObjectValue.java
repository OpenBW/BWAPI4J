package org.openbw.bwapi4j.ap;

import java.util.List;
import javax.lang.model.element.Name;

public class NewObjectValue {

  private final Name fqClassName;
  private final Name className;
  private final List<RValue> constructorArgs;
  private final boolean needsOuterClass;

  NewObjectValue(
      Name fqClassName, Name className, List<RValue> constructorArgs, boolean needsOuterClass) {
    this.fqClassName = fqClassName;
    this.className = className;
    this.constructorArgs = constructorArgs;
    this.needsOuterClass = needsOuterClass;
  }

  public List<RValue> getConstructorArgs() {
    return constructorArgs;
  }

  public Name getFqClassName() {
    return fqClassName;
  }

  public Name getClassName() {
    return className;
  }

  public boolean isNeedsOuterClass() {
    return needsOuterClass;
  }

  public int getDataAmount() {
    return constructorArgs.stream().mapToInt(RValue::getDataAmount).sum();
  }
}
