package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

class NamedAssignment extends Assignment {

  private final String name;
  private final int index;
  private final boolean initializeOnly;

  NamedAssignment(
      Name field,
      RValue rValue,
      String accessor,
      String indirection,
      boolean initializeOnly,
      String name,
      int index) {
    super(field, rValue, accessor, indirection);
    this.name = name;
    this.index = index;
    this.initializeOnly = initializeOnly;
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public boolean isInitializeOnly() {
    return initializeOnly;
  }
}
