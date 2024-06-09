package org.pzsp2.mockdevices.implementations;

import org.pzsp2.mockdevices.MockInverter;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;
import java.util.ArrayList;

import static org.pzsp2.protocoltranslation.auxiliary.randomShort;
import static org.pzsp2.protocoltranslation.auxiliary.randomCharacter;

public class WorkingInverter extends MockInverter {

  public WorkingInverter(
    int id,
    Character hardwareVersion,
    int numberOfGauges,
    int numberOfPowerModuleTemperatures
  ) {
    super(id, hardwareVersion, WorkStatus.WORKING, numberOfGauges, numberOfPowerModuleTemperatures);
  }

  @Override
  public WorkStatus generateWorkStatus() {
    return WorkStatus.WORKING;
  }

  @Override
  public InverterGaugeData generateGaugeData() {
    var gaugeData = new InverterGaugeData();
    var voltage = randomShort((short) 235, (short) 245);
    var current = randomShort((short) 38, (short) 44);
    var effectivePower = (short) (voltage * (current / 10) / 2) ;
    var effectiveEnergy = effectivePower * 60;
    var rmsVoltage = (short) (voltage * 30);
    var rmsCurrent = (short) (current * 30);
    var powerFactor = (char) (randomCharacter((char) 50, (char) 80) / 10);
    var passivePower = (short) (effectivePower * powerFactor / 100);
    gaugeData.setTemporaryVoltage(voltage);
    gaugeData.setTemporaryCurrent(current);
    gaugeData.setPower(effectivePower);
    gaugeData.setEnergy(effectiveEnergy);
    gaugeData.setRmsVoltage(rmsVoltage);
    gaugeData.setRmsCurrent(rmsCurrent);
    gaugeData.setPassivePower(passivePower);
    gaugeData.setPowerFactor(powerFactor);
    return gaugeData;
  }

  @Override
  public TotalGaugeData generateTotalGaugeData() {
    var gauges = getCurrentData().getGauges();
    var totalGaugeData = getCurrentData().getTotalGauge();
    if (totalGaugeData == null) {
      totalGaugeData = new TotalGaugeData();
      getCurrentData().setTotalGauge(totalGaugeData);
    }
    int totalEnergy = 0;
    short totalPower = 0;
    short totalPassivePower = 0;
    int totalPassiveEnergy = 0;
    for (var gauge: gauges) {
      totalEnergy += gauge.getEnergy();
      totalPower += gauge.getPower();
      totalPassiveEnergy += gauge.getPassivePower() * 60;
      totalPassivePower += gauge.getPassivePower();
    }
    totalGaugeData.setTotalEnergy(totalEnergy);
    totalGaugeData.setTotalPower(totalPower);
    totalGaugeData.setTotalPassivePower(totalPassivePower);
    totalGaugeData.setTotalPassiveEnergy(totalPassiveEnergy);
    return totalGaugeData;
  }

  @Override
  public char generateGeneratorVoltage() {
    return randomCharacter((char) 230, (char) 240);
  }

  @Override
  public char generateBoostVoltage() {
    return randomCharacter((char) 390, (char) 415);
  }

  @Override
  public short generateGeneratorCurrent() {
    return randomShort((short) 230, (short) 250);
  }

  @Override
  public ArrayList<Character> generatePowerModuleTemperatures() {
    var temperatures = new ArrayList<Character>();
    for (int i = 0; i < getNumberOfPowerModules(); i++) {
      temperatures.add(randomCharacter((char) 59, (char) 71));
    }
    return temperatures;
  }

  @Override
  public char generateCurrentGeneratorSpeed() {
    return randomCharacter((char) 390, (char) 415);
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
    return randomCharacter((char) 480, (char) 490);
  }

  @Override
  public char generateErrorCode() {
    return 0;
  }
}
