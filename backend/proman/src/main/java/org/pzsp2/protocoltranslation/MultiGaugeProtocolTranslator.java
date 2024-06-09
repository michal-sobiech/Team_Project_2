package org.pzsp2.protocoltranslation;

import org.pzsp2.protocoltranslation.inverterdata.InverterGaugeData;

public interface MultiGaugeProtocolTranslator extends ProtocolTranslator {
  InverterGaugeData translateGauge(
    int rmsVoltageRegisterId,
    int rmsCurrentRegisterId,
    int tempVoltageRegisterId,
    int tempCurrentRegisterId,
    int powerRegisterId,
    int passivePowerRegisterId,
    int powerFactorRegisterId,
    int energyRegisterId
  );
}
