package org.openbw.bwapi4j.ap;

public class DelegateAssignment {

  private final Delegate delegate;

  public DelegateAssignment(Delegate delegate) {
    this.delegate = delegate;
  }

  public Delegate getDelegate() {
    return delegate;
  }
}
