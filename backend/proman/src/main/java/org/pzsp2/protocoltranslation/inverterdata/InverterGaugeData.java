package org.pzsp2.protocoltranslation.inverterdata;

import lombok.Getter;
import lombok.Setter;

public class InverterGaugeData {
  @Getter @Setter
  Short rmsVoltage; // U
  @Getter @Setter
  Short rmsCurrent; // I
  @Getter @Setter
  Short temporaryVoltage; // Utemp
  @Getter @Setter
  Short temporaryCurrent; // Itemp
  @Getter @Setter
  Short power; // P
  @Getter @Setter
  Short passivePower; // Q
  @Getter @Setter
  Character powerFactor; // Cos(fi)
  @Getter @Setter
  Integer energy; // E

}
