package org.openbw.bwapi4j.ap;

public class RValue {

  private ListValue listValue;
  private EnumValue enumValue;
  private NewObjectValue newObjectValue;
  private PrimitiveValue primitiveValue;
  private BWMappedValue bwMappedValue;
  private String constant;

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

  public BWMappedValue getBwMappedValue() {
    return bwMappedValue;
  }

  public String getConstant() {
    return constant;
  }
}
