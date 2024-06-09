package org.pzsp2.proman.database_management.tables.inverter.service;

import java.util.List;

import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;

public interface InverterService {
    List<InverterDTO> getAllInverters();

    List<InverterDTO> getInvertersByUser(long userId);

    InverterDTO getInverterById(long inverterId);

    InverterDTO addNewInverter(Inverter inverterDTO);

    InverterDTO editInverter(Inverter inverterDTO);

    void deleteInverterById(long inverterId);
}