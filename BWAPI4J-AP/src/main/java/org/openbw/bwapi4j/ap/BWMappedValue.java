package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class BWMappedValue {

  private final String bwMethod;
  private final Name targetType;

  public BWMappedValue(String bwMethod, Name targetType) {

    this.bwMethod = bwMethod;
    this.targetType = targetType;
  }

  public String getBwMethod() {
    return bwMethod;
  }

  public Name getTargetType() {
    return targetType;
  }
}
