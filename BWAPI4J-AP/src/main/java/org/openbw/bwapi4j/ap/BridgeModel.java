package org.openbw.bwapi4j.ap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Name;

public class BridgeModel {

  private final Name packageName;
  private final Name fqName;
  private final Name name;
  private final String bridgeClassName;
  private List<Assignment> assignments;
  private Set<Delegate> delegates = new HashSet<>();

  public BridgeModel(Name packageName, Name fqName, Name name, String bridgeClassName) {

    this.packageName = packageName;
    this.fqName = fqName;
    this.name = name;
    this.bridgeClassName = bridgeClassName;
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

  public void setAssignments(List<Assignment> assignments) {
    this.assignments = assignments;
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public Set<Delegate> getDelegates() {
    return delegates;
  }

  public void addDelegate(Delegate delegate) {
    delegates.add(delegate);
  }
}
