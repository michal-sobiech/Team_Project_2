package org.pzsp2.protocoltranslation.inverterdata;

import lombok.Getter;
import lombok.Setter;

public class TotalGaugeData {
  @Getter @Setter
  Short totalPower;
  @Getter @Setter
  Integer totalEnergy;
  @Getter @Setter
  Short totalPassivePower;
  @Getter @Setter
  Integer totalPassiveEnergy;
}
