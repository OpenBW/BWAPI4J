package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class Assignment {

  private final Name field;
  private RValue rValue;
  private DelegateAssignment delegateAssignment;

  Assignment(Name field, RValue rValue) {
    this.field = field;
    this.rValue = rValue;
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
}
