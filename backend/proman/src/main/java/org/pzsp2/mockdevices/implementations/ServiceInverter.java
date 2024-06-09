package org.pzsp2.mockdevices.implementations;

import org.pzsp2.mockdevices.MockInverter;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;

import java.util.ArrayList;

public class ServiceInverter extends MockInverter {
  public ServiceInverter(
    int id,
    Character hardwareVersion,
    int numberOfGauges,
    int numberOfPowerModuleTemperatures
  ) {
    super(id, hardwareVersion, WorkStatus.SERVICE, numberOfGauges, numberOfPowerModuleTemperatures);
  }

  @Override
  public WorkStatus generateWorkStatus() {
    return WorkStatus.SERVICE;
  }

  @Override
  public InverterGaugeData generateGaugeData() {
    return new InverterGaugeData();

  }

  @Override
  public TotalGaugeData generateTotalGaugeData() {
    return new TotalGaugeData();
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

  @Override
  public char generateErrorCode() {
    return 0;
  }
}
