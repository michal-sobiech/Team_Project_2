package org.pzsp2.mockdevices;

import org.pzsp2.protocoltranslation.inverterdata.InverterData;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;

import java.util.ArrayList;

public interface MockInverterInterface {
  InverterData generateData();
  WorkStatus generateWorkStatus();

  InverterGaugeData generateGaugeData();

  TotalGaugeData generateTotalGaugeData();

  char generateGeneratorVoltage();

  char generateBoostVoltage();

  short generateGeneratorCurrent();

  ArrayList<Character> generatePowerModuleTemperatures();

  char generateCurrentGeneratorSpeed();

  char generateNetworkFrequency();

  short generateRelayStatus();

  char generateHeaterPower();

  char generateErrorCode();
}
