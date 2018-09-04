package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class Assignment {

  private final Name field;
  private RValue rValue;
  private String accessor;
  private String indirection;
  private Delegate byDelegate;

  Assignment(
      Name field,
      RValue rValue,
      String accessor,
      String indirection) {
    this.field = field;
    this.rValue = rValue;
    this.accessor = accessor;
    this.indirection = indirection;
  }

  Assignment(Name field, Delegate byDelegate) {
    this.field = field;
    this.byDelegate = byDelegate;
  }

  public Name getField() {
    return field;
  }

  public RValue getRValue() {
    return rValue;
  }

  public Delegate getByDelegate() {
    return byDelegate;
  }

  public String getAccessor() {
    return accessor;
  }

  public String getIndirection() {
    return indirection;
  }
}
