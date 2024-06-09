package org.pzsp2.proman.database_management.tables.admin.repository;

import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByLogin(String login);
}