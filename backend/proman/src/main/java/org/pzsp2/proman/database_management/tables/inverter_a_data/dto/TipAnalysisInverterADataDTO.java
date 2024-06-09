package org.pzsp2.proman.database_management.tables.inverter_a_data.dto;

public record TipAnalysisInverterADataDTO(int workingStatus, int effectiveUA,
        int effectiveUB, int effectiveUC, int effectiveIA, int effectiveIB, int effectiveIC, int instantaneousUA, int instantaneousUB,
        int instantaneousUC, int instantaneousIA, int instantaneousIB, int instantaneousIC, int Udc1, int Udc2, int Idc, int PA,
        int PB, int PC, int P, int olderEA, int newerEA, int olderEB, int newerEB, int olderEC, int newerEC, int olderE, int newerE,
        int QA, int QB, int QC, int Q, int olderEQ, int newerEQ, int cosA, int cosB, int cosC, int T1, int T2, int generatorRotation, int F) {

    public static TipAnalysisInverterADataDTO of(final InverterADataDTO inverterADataDTO) {
        return new TipAnalysisInverterADataDTO(
            inverterADataDTO.workingStatus(),
            inverterADataDTO.effectiveUA(),
            inverterADataDTO.effectiveUB(),
            inverterADataDTO.effectiveUC(),
            inverterADataDTO.effectiveIA(),
            inverterADataDTO.effectiveIB(),
            inverterADataDTO.effectiveIC(),
            inverterADataDTO.instantaneousUA(),
            inverterADataDTO.instantaneousUB(),
            inverterADataDTO.instantaneousUC(),
            inverterADataDTO.instantaneousIA(),
            inverterADataDTO.instantaneousIB(),
            inverterADataDTO.instantaneousIC(),
            inverterADataDTO.Udc1(),
            inverterADataDTO.Udc2(),
            inverterADataDTO.Idc(),
            inverterADataDTO.PA(),
            inverterADataDTO.PB(),
            inverterADataDTO.PC(),
            inverterADataDTO.P(),
            inverterADataDTO.olderEA(),
            inverterADataDTO.newerEA(),
            inverterADataDTO.olderEB(),
            inverterADataDTO.newerEB(),
            inverterADataDTO.olderEC(),
            inverterADataDTO.newerEC(),
            inverterADataDTO.olderE(),
            inverterADataDTO.newerE(),
            inverterADataDTO.QA(),
            inverterADataDTO.QB(),
            inverterADataDTO.QC(),
            inverterADataDTO.Q(),
            inverterADataDTO.olderEQ(),
            inverterADataDTO.newerEQ(),
            inverterADataDTO.cosA(),
            inverterADataDTO.cosB(),
            inverterADataDTO.cosC(),
            inverterADataDTO.T1(),
            inverterADataDTO.T2(),
            inverterADataDTO.generatorRotation(),
            inverterADataDTO.F()
        );
    }

    public TipAnalysisInverterADataDTO() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }
}