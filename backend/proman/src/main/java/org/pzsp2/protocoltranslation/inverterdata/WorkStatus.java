package org.pzsp2.protocoltranslation.inverterdata;

import lombok.Getter;

public enum WorkStatus {
  UNDEFINED(-1),
  ERROR(0),
  STARTING(1),
  WORKING(2),
  SERVICE(3),
  ;
  @Getter
  final int statusIdentifier;
  WorkStatus(int identifier) {
    statusIdentifier = identifier;
  }
  public static WorkStatus fromIdentifier(int statusIdentifier) {
    for (var workStatus: WorkStatus.values()) {
      if (statusIdentifier == workStatus.getStatusIdentifier()) {
        return workStatus;
      }
    }
    return null;
  }

}
