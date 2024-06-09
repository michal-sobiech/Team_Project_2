package org.pzsp2.mockdevices.implementations;

import org.pzsp2.mockdevices.MockInverter;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;

import java.util.ArrayList;

abstract public class BrokenInverter extends MockInverter {
  
  public BrokenInverter(
    int id,
    Character hardwareVersion,
    int numberOfGauges,
    int numberOfPowerModuleTemperatures
  ) {
    super(id, hardwareVersion, WorkStatus.ERROR, numberOfGauges, numberOfPowerModuleTemperatures);
  }

  @Override
  public WorkStatus generateWorkStatus() {
    return WorkStatus.ERROR;
  }

  @Override
  public InverterGaugeData generateGaugeData() {
    return null;
  }

  @Override
  public TotalGaugeData generateTotalGaugeData() {
    return null;
  }

  @Override
  public char generateGeneratorVoltage() {
    return 0;
  }

  @Override
  public char generateBoostVoltage() {
    return 0;
  }

  @Override
  public short generateGeneratorCurrent() {
    return 0;
  }

  @Override
  public ArrayList<Character> generatePowerModuleTemperatures() {
    return null;
  }

  @Override
  public char generateCurrentGeneratorSpeed() {
    return 0;
  }

  @Override
  public char generateNetworkFrequency() {
    return 0;
  }

  @Override
  public short generateRelayStatus() {
    return 0;
  }

  @Override
  public char generateHeaterPower() {
    return 0;
  }
}
