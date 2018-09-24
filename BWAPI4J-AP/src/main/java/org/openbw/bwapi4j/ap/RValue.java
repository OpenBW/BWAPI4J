package org.openbw.bwapi4j.ap;

public class RValue {

  private MapValue mapValue;
  private ListValue listValue;
  private EnumValue enumValue;
  private NewObjectValue newObjectValue;
  private PrimitiveValue primitiveValue;
  private BWMappedValue bwMappedValue;
  private ArrayValue arrayValue;
  private String constant;
  private boolean string;

  public RValue(EnumValue enumValue) {
    this.enumValue = enumValue;
  }

  public RValue(PrimitiveValue primitiveValue) {
    this.primitiveValue = primitiveValue;
  }

  public RValue(ListValue listValue) {
    this.listValue = listValue;
  }

  public RValue(NewObjectValue newObjectValue) {
    this.newObjectValue = newObjectValue;
  }

  public RValue(BWMappedValue bwMappedValue) {
    this.bwMappedValue = bwMappedValue;
  }

  public RValue(String constant) {
    this.constant = constant;
  }

  public RValue(MapValue mapValue) {
    this.mapValue = mapValue;
  }

  public RValue(ArrayValue arrayValue) {
    this.arrayValue = arrayValue;
  }

  public RValue() {
    string = true;
  }

  public EnumValue getEnumValue() {
    return enumValue;
  }

  public NewObjectValue getNewObjectValue() {
    return newObjectValue;
  }

  public PrimitiveValue getPrimitiveValue() {
    return primitiveValue;
  }

  public ListValue getListValue() {
    return listValue;
  }

  public MapValue getMapValue() {
    return mapValue;
  }

  public BWMappedValue getBwMappedValue() {
    return bwMappedValue;
  }

  public ArrayValue getArrayValue() {
    return arrayValue;
  }

  public String getConstant() {
    return constant;
  }

  public boolean isString() {
    return string;
  }

  public int getDataAmount() {
    if (enumValue != null) {
      return 1;
    }
    if (newObjectValue != null) {
      return newObjectValue.getDataAmount();
    }
    if (primitiveValue != null) {
      return 1;
    }
    if (bwMappedValue != null) {
      return 1;
    }
    throw new IllegalStateException("Invalid type of RValue for data amount!");
  }
}
