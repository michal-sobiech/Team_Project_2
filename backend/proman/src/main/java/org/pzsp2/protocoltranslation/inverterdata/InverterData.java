package org.pzsp2.protocoltranslation.inverterdata;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

public class InverterData {
  public InverterData() {
    gauges = new ArrayList<InverterGaugeData>();
    totalGauge = new TotalGaugeData();
    powerModuleTemperatures = new ArrayList<Character>();
  }


  @Getter @Setter
  WorkStatus workStatus;
  @Getter @Setter
  Character hardwareVersion;
  @Getter @Setter
  ArrayList<InverterGaugeData> gauges;
  @Getter @Setter
  TotalGaugeData totalGauge;
  @Getter @Setter
  Character generatorVoltage;
  @Getter @Setter
  Character boostVoltage;
  @Getter @Setter
  Short generatorCurrent;
  @Getter @Setter
  ArrayList<Character> powerModuleTemperatures;
  @Getter @Setter
  Character currentGeneratorSpeed;
  @Getter @Setter
  Character networkFrequency;
  @Getter @Setter
  Short relayStatus;
  @Getter @Setter
  Character heaterPower;
  @Getter @Setter
  Character errorCode;
}
