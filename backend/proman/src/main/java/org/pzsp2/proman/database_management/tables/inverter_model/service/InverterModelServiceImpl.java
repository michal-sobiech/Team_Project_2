package org.pzsp2.proman.database_management.tables.inverter_model.service;

import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;
import org.pzsp2.proman.database_management.tables.inverter_model.repository.InverterModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InverterModelServiceImpl implements InverterModelService {

    private final InverterModelRepository inverterModelRepository;

    @Autowired
    public InverterModelServiceImpl(InverterModelRepository inverterModelRepository) {
        this.inverterModelRepository = inverterModelRepository;
    }

    @Override
    public List<InverterModel> getAllInverterModels() {
        return inverterModelRepository.findAll();
    }

    @Override
    public InverterModel getInverterModelById(Long id) {
        return inverterModelRepository.findById(id).orElse(null);
    }

    @Override
    public void saveInverterModel(InverterModel inverterModel) {
        inverterModelRepository.save(inverterModel);
    }

    @Override
    public void deleteInverterModel(Long id) {
        inverterModelRepository.deleteById(id);
    }
}
