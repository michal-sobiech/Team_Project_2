package org.pzsp2.proman.database_management.tables.inverter_a_data.service;

import java.util.List;

import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;

public interface InverterADataService {

    List<InverterADataDTO> getDataByInverterId(long inverterId);

    InverterADataDTO saveData(InverterADataDTO inverterADataDTO);
}
