package org.pzsp2.proman.database_management.tables.privileges.repository;

import org.pzsp2.proman.database_management.tables.privileges.model.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Long> {

}
