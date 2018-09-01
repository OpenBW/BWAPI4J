package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class Assignment {

  private final Name field;
  private RValue rValue;
  private String accessor;
  private DelegateAssignment delegateAssignment;

  Assignment(Name field, RValue rValue, String accessor) {
    this.field = field;
    this.rValue = rValue;
    this.accessor = accessor;
  }

  Assignment(Name field, DelegateAssignment delegateAssignment) {
    this.field = field;
    this.delegateAssignment = delegateAssignment;
  }

  public Name getField() {
    return field;
  }

  public RValue getRValue() {
    return rValue;
  }

  public DelegateAssignment getDelegateAssignment() {
    return delegateAssignment;
  }

  public String getAccessor() {
    return accessor;
  }
}
