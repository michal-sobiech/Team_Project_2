package org.pzsp2.proman.database_management.tables.privileges.service;

import org.pzsp2.proman.database_management.tables.privileges.model.Privileges;
import org.pzsp2.proman.database_management.tables.privileges.repository.PrivilegesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegesServiceImpl implements PrivilegesService {

    private final PrivilegesRepository privilegesRepository;

    @Autowired
    public PrivilegesServiceImpl(PrivilegesRepository privilegesRepository) {
        this.privilegesRepository = privilegesRepository;
    }

    @Override
    public Privileges getPrivilegesById(long privilegesId) {
        return privilegesRepository.findById(privilegesId).orElse(null);
    }

    @Override
    public String getPrivilegesDescription(long privilegeId) {
        Privileges privileges = privilegesRepository.findById(privilegeId).orElse(null);
        return (privileges != null) ? privileges.getDescription() : null;
    }
}