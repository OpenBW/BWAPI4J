package org.openbw.bwapi4j.ap;

import java.util.ArrayList;
import java.util.List;

public class Assignments {

  private List<NamedAssignment> namedAssignments = new ArrayList<>();
  private List<Assignment> delegatedAssignments = new ArrayList<>();
  private List<Assignment> assignments = new ArrayList<>();
  private List<Assignment> resetAssignments = new ArrayList<>();
  int namedFieldIndex = 0;

  public int getFirstDelegateIndex() {
    return namedFieldIndex;
  }

  public void addAssignment(Assignment assignment) {
    assignments.add(assignment);
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public void addResetAssignment(Assignment assignment) {
    resetAssignments.add(assignment);
  }

  public List<Assignment> getResetAssignments() {
    return resetAssignments;
  }

  public void addNamedAssignment(NamedAssignment namedAssignment) {
    namedAssignments.add(namedAssignment);
  }

  public List<NamedAssignment> getNamedAssignments() {
    return namedAssignments;
  }

  public void addDelegatedAssignment(Assignment delegatedAssignment) {
    delegatedAssignments.add(delegatedAssignment);
  }

  public List<Assignment> getDelegatedAssignments() {
    return delegatedAssignments;
  }
}
