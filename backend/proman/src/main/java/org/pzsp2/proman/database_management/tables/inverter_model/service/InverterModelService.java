package org.pzsp2.proman.database_management.tables.inverter_model.service;

import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;

import java.util.List;

public interface InverterModelService {
    List<InverterModel> getAllInverterModels();
    InverterModel getInverterModelById(Long id);
    void saveInverterModel(InverterModel inverterModel);
    void deleteInverterModel(Long id);
}