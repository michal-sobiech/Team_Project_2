package org.pzsp2.protocoltranslation;

import org.pzsp2.protocoltranslation.inverterdata.InverterData;

public interface ProtocolTranslator {
  InverterData translate(byte[] inputData);
}