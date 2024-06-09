package org.pzsp2.protocoltranslation;

import org.pzsp2.protocoltranslation.inverterdata.InverterData;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;

public class MODBUSTranslator
  implements
  MultiGaugeProtocolTranslator,
  TotalGaugeProtocolTranslator {
  byte[] inputBytes;
  @Override
  public InverterData translate(byte[] bytes) {
    inputBytes = bytes;
    InverterGaugeData aGauge = translateGauge( 3, 6, 9, 12, 18, 30, 36, 22 );
    InverterGaugeData bGauge = translateGauge( 3 + 1, 6 + 1, 9 + 1, 12 + 1, 18 + 1, 30 + 1, 36 + 1, 22 + 1 );
    InverterGaugeData cGauge = translateGauge( 3 + 2, 6 + 2, 9 + 2, 12 + 2, 18 + 2, 30 + 2, 36 + 2, 22 + 2 );
    var translatedData = new InverterData();
    translatedData.setWorkStatus(translateWorkStatus());
    translatedData.setHardwareVersion(getUInt16Register(2));
    translatedData.getGauges().add(aGauge);
    translatedData.getGauges().add(bGauge);
    translatedData.getGauges().add(cGauge);
    translatedData.setTotalGauge(translateTotal());
    translatedData.setGeneratorVoltage(getUInt16Register(15));
    translatedData.setBoostVoltage(getUInt16Register(16));
    translatedData.setGeneratorCurrent(getInt16Register(17));
    translatedData.getPowerModuleTemperatures().add(getUInt16Register(39));
    translatedData.getPowerModuleTemperatures().add(getUInt16Register(40));
    translatedData.setCurrentGeneratorSpeed(getUInt16Register(42));
    translatedData.setNetworkFrequency(getUInt16Register(43));
    translatedData.setRelayStatus(getInt16Register(44));
    translatedData.setHeaterPower(getUInt16Register(46));
    translatedData.setErrorCode(getUInt16Register(48));
    return translatedData;
  }

  @Override
  public InverterGaugeData translateGauge(
    int rmsVoltageRegisterId,
    int rmsCurrentRegisterId,
    int tempVoltageRegisterId,
    int tempCurrentRegisterId,
    int powerRegisterId,
    int passivePowerRegisterId,
    int powerFactorRegisterId,
    int energyRegisterId
  ) {
    var gauge = new InverterGaugeData();
    gauge.setRmsVoltage(getInt16Register(rmsVoltageRegisterId));
    gauge.setRmsCurrent(getInt16Register(rmsCurrentRegisterId));
    gauge.setTemporaryVoltage(getInt16Register(tempVoltageRegisterId));
    gauge.setTemporaryCurrent(getInt16Register(tempCurrentRegisterId));
    gauge.setPower(getInt16Register(powerRegisterId));
    gauge.setPassivePower(getInt16Register(passivePowerRegisterId));
    gauge.setPowerFactor(getUInt16Register(powerFactorRegisterId));
    gauge.setEnergy(getInt32Register(energyRegisterId));
    return gauge;
  }

  @Override
  public TotalGaugeData translateTotal(
//    int totalPowerRegisterId, int totalEnergyRegisterId,
//    int totalPassivePower, int totalPassiveEnergy
  ) {
    var totalGauge = new TotalGaugeData();
    totalGauge.setTotalPower(getInt16Register(21));
    totalGauge.setTotalEnergy(getInt32Register(28));
    totalGauge.setTotalPassivePower(getInt16Register(33));
    totalGauge.setTotalPassiveEnergy(getInt32Register(34));
    return totalGauge;
  }

  public WorkStatus translateWorkStatus() {
    char workStatusChar = auxiliary.characterFromBytes(inputBytes[1], inputBytes[2]);
    return WorkStatus.fromIdentifier(workStatusChar);
  }

  public char getUInt16Register(int registerId) {
    int location = registerId * 2 - 1;
    return auxiliary.characterFromBytes(inputBytes[location], inputBytes[location + 1]);
  }

  public short getInt16Register(int registerId) {
    int location = registerId * 2 - 1;
    return auxiliary.shortFromBytes(inputBytes[location], inputBytes[location + 1]);
  }

  public int getInt32Register(int registerId) {
    int location = registerId * 2 - 1;
    return auxiliary.intFromBytes(
      inputBytes[location],
      inputBytes[location + 1],
      inputBytes[location + 2],
      inputBytes[location + 3]
    );
  }
}
