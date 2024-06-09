export default class InverterADataDO {
  constructor(dataSet) {
    this.inverterADataId = dataSet.inverterADataId;
    this.inverterId = dataSet.inverterId;
    this.measureDate = dataSet.measureDate;
    this.workingStatus = dataSet.workingStatus;
    this.effectiveUA = dataSet.effectiveUA;
    this.effectiveUB = dataSet.effectiveUB;
    this.effectiveUC = dataSet.effectiveUB;
    this.effectiveIA = dataSet.effectiveIA;
    this.effectiveIB = dataSet.effectiveIB;
    this.effectiveIC = dataSet.effectiveIC;
    this.instantaneousUA = dataSet.instantaneousUA;
    this.instantaneousUB = dataSet.instantaneousUB;
    this.instantaneousUC = dataSet.instantaneousUC;
    this.instantaneousIA = dataSet.instantaneousIA;
    this.instantaneousIB = dataSet.instantaneousIB;
    this.instantaneousIC = dataSet.instantaneousIC;
    this.Udc1 = dataSet.Udc1;
    this.Udc2 = dataSet.Udc2
    this.Idc = dataSet.Idc;
    this.PA = dataSet.PA;
    this.PB = dataSet.PB;
    this.PC = dataSet.PC;
    this.P = dataSet.P;
    this.olderEA = dataSet.olderEA;
    this.newerEA = dataSet.newerEA;
    this.olderEB = dataSet.olderEB;
    this.newerEB = dataSet.newerEB;
    this.olderEC = dataSet.olderEC;
    this.newerEC = dataSet.newerEC;
    this.olderE = dataSet.olderE;
    this.newerE = dataSet.newerE;
    this.QA = dataSet.QA;
    this.QB = dataSet.QB;
    this.QC = dataSet.QC;
    this.Q = dataSet.Q;
    this.olderEQ = dataSet.olderEQ;
    this.newerEQ = dataSet.newerEQ
    this.cosA = dataSet.cosA;
    this.cosB = dataSet.cosB;
    this.cosC = dataSet.cosC;
    this.T1 = dataSet.T1;
    this.T2 = dataSet.T2
    this.generatorRotation = dataSet.generatorRotation;
    this.F = dataSet.F;
  }
}