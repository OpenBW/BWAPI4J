package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class UnitMock extends UnitImpl {
  public UnitMock(int id, UnitType unitType) {
    this.iD = id;
    this.type = unitType;
  }
}
