package org.pzsp2.proman.database_management.tables.owner.service;

import org.pzsp2.proman.database_management.tables.owner.dto.OwnerDTO;
import org.pzsp2.proman.database_management.tables.owner.model.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> getAllOwners();

    OwnerDTO mapToDTO(Owner owner);

    Owner getOwnerById(long ownerId);
    Owner findByLogin(String login);
}
