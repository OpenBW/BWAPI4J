package org.openbw.bwapi4j.ap;

import java.util.List;
import java.util.Objects;

public class Delegate {

  private final String fqClassName;
  private final String fieldName;
  private final List<Assignment> assignments;

  public Delegate(String fqClassName, String fieldName,
      List<Assignment> assignments) {
    this.fqClassName = fqClassName;
    this.fieldName = fieldName;
    this.assignments = assignments;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getFqClassName() {
    return fqClassName;
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delegate delegate = (Delegate) o;
    return Objects.equals(fqClassName, delegate.fqClassName) &&
        Objects.equals(fieldName, delegate.fieldName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(fqClassName, fieldName);
  }
}
