package org.pzsp2.proman.database_management.tables.privileges.service;

import org.pzsp2.proman.database_management.tables.privileges.model.Privileges;

public interface PrivilegesService {
    Privileges getPrivilegesById(long privilegeId);

    String getPrivilegesDescription(long privilegeId);
}