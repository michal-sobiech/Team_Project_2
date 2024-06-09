package org.pzsp2.proman.database_management.tables.inverter.service;

import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.repository.InverterRepository;
import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class InverterServiceImpl implements InverterService {

    private final InverterRepository inverterRepository;

    public InverterServiceImpl(final InverterRepository inverterRepository) {
        this.inverterRepository = inverterRepository;
    }

    @Override
    public List<InverterDTO> getAllInverters() {
        return inverterRepository.findAll().stream().map(InverterDTO::of).toList();
    }

    @Override
    public List<InverterDTO> getInvertersByUser(long userId) {
        final List<Inverter> inverters = inverterRepository.findByUserId(userId);
        return inverters.stream().map(InverterDTO::of).toList();
    }

    @Override
    public InverterDTO getInverterById(long inverterId) {
        return InverterDTO.of(Objects.requireNonNull(inverterRepository.findById(inverterId).orElse(null)));
    }

    @Override
    public InverterDTO addNewInverter(Inverter inverterToAdd) {
        System.out.println("tworze invertera w service");
//        final Inverter newInverter = new Inverter(inverterDTO.getUserId(), inverterDTO.getIpAddress());
        inverterRepository.save(inverterToAdd);
        return InverterDTO.of(inverterToAdd);
    }

    @Override
    public InverterDTO editInverter(Inverter inverterToEdit) {
        Inverter existingInverter = inverterRepository.findById(inverterToEdit.getInverterId())
                .orElseThrow(() -> new IllegalArgumentException("Inverter does not exist"));
        existingInverter.setUserId(inverterToEdit.getUserId());
        existingInverter.setLogin((inverterToEdit.getLogin()));
        existingInverter.setIpAddress(inverterToEdit.getIpAddress());
        existingInverter.setCode(inverterToEdit.getCode());
        existingInverter.setModel(inverterToEdit.getModel());
        if (inverterToEdit.getPassword() == null || inverterToEdit.getPassword().isEmpty()) {
            inverterToEdit.setPassword(existingInverter.getPassword());
        }
        existingInverter.setPassword(inverterToEdit.getPassword());
        inverterRepository.save(existingInverter);
        return InverterDTO.of(existingInverter);
    }

    @Override
    public void deleteInverterById(long inverterId) {
        inverterRepository.deleteById(inverterId);
    }
}