package org.pzsp2.proman.database_management.tables.inverter_a_data.service;

import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.model.InverterAData;
import org.pzsp2.proman.database_management.tables.inverter_a_data.repository.InverterADataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InverterADataServiceImpl implements InverterADataService{

    private final InverterADataRepository inverterADataRepository;

    public InverterADataServiceImpl(final InverterADataRepository inverterADataRepository) {
        this.inverterADataRepository = inverterADataRepository;
    }

    @Override
    public List<InverterADataDTO> getDataByInverterId(long inverterId) {
        final List<InverterAData> dataList = inverterADataRepository.findByInverterId(inverterId);
        return dataList.stream().map(InverterADataDTO::of).toList();
    }

    @Override
    public InverterADataDTO saveData(InverterADataDTO inverterADataDTO) {
        final InverterAData newInverterAData = new InverterAData(
            inverterADataDTO.inverterId(),
            inverterADataDTO.measureDate(),
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
        inverterADataRepository.save(newInverterAData);
        return InverterADataDTO.of(newInverterAData);
    }
}
