package org.pzsp2.mockdevices.implementations;


public class SomeBrokenInverter extends BrokenInverter {
  public SomeBrokenInverter(
    int id,
    Character hardwareVersion,
    int numberOfGauges,
    int numberOfPowerModuleTemperatures
  ) {
    super(id, hardwareVersion, numberOfGauges, numberOfPowerModuleTemperatures);
  }

  @Override
  public char generateErrorCode() {
    return 1;
  }
}
