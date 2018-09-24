package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class EnumValue {

  private final Name enumName;

  public EnumValue(Name enumName) {
    this.enumName = enumName;
  }

  public Name getEnumName() {
    return enumName;
  }
}
