package org.pzsp2.mockdevices;
import lombok.Getter;
import lombok.Setter;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;
import org.pzsp2.protocoltranslation.inverterdata.InverterData;
import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.TotalGaugeData;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;

abstract public class MockInverter implements MockInverterInterface {
  @Getter @Setter private int id;
  @Getter @Setter private ZonedDateTime lastMeasurementsDate;
  @Getter @Setter private InverterData currentData;
  @Getter @Setter private int numberOfPowerModules;
  @Getter @Setter private int numberOfGauges;

  public MockInverter() {
    currentData = new InverterData();
    numberOfPowerModules = 0;
    numberOfGauges = 0;
    id = -1;
  }

  public MockInverter(
    int id,
    Character hardwareVersion,
    WorkStatus workStatus,
    int numberOfGauges,
    int numberOfPowerModuleTemperatures
  ) {
    this.id = id;
    this.numberOfPowerModules = numberOfPowerModuleTemperatures;
    this.numberOfGauges = numberOfGauges;
    this.currentData = new InverterData();
    int i;
    for (i = 0; i < numberOfGauges; i++) {
      currentData.getGauges().add(new InverterGaugeData());
    }

    currentData.setWorkStatus(workStatus);
    currentData.setHardwareVersion(hardwareVersion);
  }
  public InverterData generateData() {
    currentData.setWorkStatus(generateWorkStatus());
    int i;
    for (i = 0; i < numberOfGauges; i++) {
      currentData.getGauges().set(i, generateGaugeData());
    }
    currentData.setTotalGauge(generateTotalGaugeData());
    currentData.setGeneratorVoltage(generateGeneratorVoltage());
    currentData.setBoostVoltage(generateBoostVoltage());
    currentData.setGeneratorCurrent(generateGeneratorCurrent());
    currentData.setPowerModuleTemperatures(generatePowerModuleTemperatures());
    currentData.setCurrentGeneratorSpeed(generateCurrentGeneratorSpeed());
    currentData.setNetworkFrequency(generateNetworkFrequency());
    currentData.setRelayStatus(generateRelayStatus());
    currentData.setHeaterPower(generateHeaterPower());
    currentData.setErrorCode(generateErrorCode());
    return currentData;
  }

  public InverterADataDTO toInverterADataDTO() {
    generateData();
    return new InverterADataDTO(
//    Long inverterADataId,
      null,
//    Long inverterId,
      (long) getId(),
//    ZonedDateTime measureDate,
      null,
//    int workingStatus,
      currentData.getWorkStatus().getStatusIdentifier(),
//    int effectiveUA,
      currentData.getGauges().get(0).getRmsVoltage(),
//    int effectiveUB,
      currentData.getGauges().get(1).getRmsVoltage(),
//    int effectiveUC,
      currentData.getGauges().get(2).getRmsVoltage(),
//    int effectiveIA,
      currentData.getGauges().get(0).getRmsCurrent(),
//    int effectiveIB,
      currentData.getGauges().get(1).getRmsCurrent(),
//    int effectiveIC,
      currentData.getGauges().get(2).getRmsCurrent(),
//    int instantaneousUA,
      currentData.getGauges().get(0).getTemporaryVoltage(),
//    int instantaneousUB,
      currentData.getGauges().get(1).getTemporaryVoltage(),
//    int instantaneousUC,
      currentData.getGauges().get(2).getTemporaryVoltage(),
//    int instantaneousIA,
      currentData.getGauges().get(0).getTemporaryCurrent(),
//    int instantaneousIB,
      currentData.getGauges().get(1).getTemporaryCurrent(),
//    int instantaneousIC,
      currentData.getGauges().get(2).getTemporaryCurrent(),
//    int Udc1,
      currentData.getGeneratorCurrent(),
//    int Udc2,
      currentData.getBoostVoltage(),
//    int Idc,
      currentData.getGeneratorCurrent(),
//    int PA,
      currentData.getGauges().get(0).getPower(),
//    int PB,
      currentData.getGauges().get(1).getPower(),
//    int PC,
      currentData.getGauges().get(2).getPower(),
//    int P,
      currentData.getTotalGauge().getTotalPower(),
//    int olderEA,
      currentData.getGauges().get(0).getPower(),
//    int newerEA,
      currentData.getGauges().get(0).getPower(),
//    int olderEB,
      currentData.getGauges().get(1).getPower(),
//    int newerEB,
      currentData.getGauges().get(1).getPower(),
//    int olderEC,
      currentData.getGauges().get(2).getPower(),
//    int newerEC,
      currentData.getGauges().get(2).getPower(),
//    int olderE,
      currentData.getTotalGauge().getTotalEnergy(),
//    int newerE,
      currentData.getTotalGauge().getTotalEnergy(),
//    int QA,
      currentData.getGauges().get(0).getPassivePower(),
//    int QB,
      currentData.getGauges().get(1).getPassivePower(),
//    int QC,
      currentData.getGauges().get(2).getPassivePower(),
//    int Q,
      currentData.getGauges().get(0).getTemporaryVoltage(),
//    int olderEQ,
      currentData.getTotalGauge().getTotalPassiveEnergy(),
//    int newerEQ,
      currentData.getTotalGauge().getTotalPassiveEnergy(),
//    int cosA,
      currentData.getGauges().get(0).getPowerFactor(),
//    int cosB,
      currentData.getGauges().get(1).getPowerFactor(),
//    int cosC,
      currentData.getGauges().get(2).getPassivePower(),
//    int T1,
      currentData.getPowerModuleTemperatures().get(0),
//    int T2,
      currentData.getPowerModuleTemperatures().get(1),
//    int generatorRotation,
      currentData.getCurrentGeneratorSpeed(),
//    int F
      currentData.getNetworkFrequency()
    );
  }

  abstract public WorkStatus generateWorkStatus();

  abstract public InverterGaugeData generateGaugeData();

  abstract public TotalGaugeData generateTotalGaugeData();

  abstract public char generateGeneratorVoltage();

  abstract public char generateBoostVoltage();

  abstract public short generateGeneratorCurrent();

  abstract public ArrayList<Character> generatePowerModuleTemperatures();

  abstract public char generateCurrentGeneratorSpeed();

  abstract public char generateNetworkFrequency();

  abstract public short generateRelayStatus();

  abstract public char generateHeaterPower();

  abstract public char generateErrorCode();
}
