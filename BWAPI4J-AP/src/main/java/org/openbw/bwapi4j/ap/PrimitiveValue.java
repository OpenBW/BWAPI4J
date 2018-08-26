package org.openbw.bwapi4j.ap;

import java.util.Objects;

public class PrimitiveValue {

  private final String typeName;

  public PrimitiveValue(String typeName) {
    Objects.requireNonNull(typeName);
    this.typeName = typeName;
  }

  public String getTypeName() {
    return typeName;
  }
}
