package org.openbw.bwapi4j.ap;

import javax.lang.model.element.Name;

public class Assignment {

  private final Name field;
  private RValue rValue;
  private String accessor;
  private String indirection;
  private boolean initializeOnly;
  private String namedIndex;
  private DelegateAssignment delegateAssignment;

  Assignment(
      Name field,
      RValue rValue,
      String accessor,
      String indirection,
      boolean initializeOnly,
      String namedIndex) {
    this.field = field;
    this.rValue = rValue;
    this.accessor = accessor;
    this.indirection = indirection;
    this.initializeOnly = initializeOnly;
    this.namedIndex = namedIndex;
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

  public boolean isInitializeOnly() {
    return initializeOnly;
  }

  public String getNamedIndex() {
    return namedIndex;
  }

  public String getIndirection() {
    return indirection;
  }
}
