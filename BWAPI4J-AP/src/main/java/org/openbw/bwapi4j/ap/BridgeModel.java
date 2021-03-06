package org.openbw.bwapi4j.ap;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Name;

public class BridgeModel {

  private final Name packageName;
  private final Name fqName;
  private final Name name;
  private final String bridgeClassName;
  private final String nativeClassName;
  private final String nativeClassParentName;
  private final String accessOperator;
  private Set<Delegate> delegates = new HashSet<>();
  private Assignments assignments;

  public BridgeModel(
      Name packageName,
      Name fqName,
      Name name,
      String bridgeClassName,
      String nativeClassName,
      String nativeClassParentName,
      String accessOperator) {

    this.packageName = packageName;
    this.fqName = fqName;
    this.name = name;
    this.bridgeClassName = bridgeClassName;
    this.nativeClassName = nativeClassName;
    this.nativeClassParentName = nativeClassParentName;
    this.accessOperator = accessOperator;
  }

  public Name getPackageName() {
    return packageName;
  }

  public Name getFqName() {
    return fqName;
  }

  public Name getName() {
    return name;
  }

  public String getBridgeClassName() {
    return bridgeClassName;
  }

  public Set<Delegate> getDelegates() {
    return delegates;
  }

  public void addDelegate(Delegate delegate) {
    delegates.add(delegate);
  }

  public String getNativeClassName() {
    return nativeClassName;
  }

  public String getNativeClassParentName() {
    return nativeClassParentName;
  }

  public Assignments getAssignments() {
    return assignments;
  }

  public void setAssignments(Assignments assignments) {
    this.assignments = assignments;
  }

  public String getAccessOperator() {
    return accessOperator;
  }
}
