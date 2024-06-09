package org.pzsp2.proman.database_management.tables.owner.service;

import org.pzsp2.proman.database_management.tables.owner.dto.OwnerDTO;
import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.pzsp2.proman.database_management.tables.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public OwnerDTO mapToDTO(Owner owner) {
        return new OwnerDTO(
                owner.getId(),
                owner.getName(),
                owner.getSurname(),
                owner.getLogin(),
                owner.getPassword()
        );
    }

    @Override
    public Owner getOwnerById(long ownerId) {
        return ownerRepository.findById(ownerId).orElse(null);
    }
    @Override
    public Owner findByLogin(String login) {
        return ownerRepository.findByLogin(login);
    }
}
