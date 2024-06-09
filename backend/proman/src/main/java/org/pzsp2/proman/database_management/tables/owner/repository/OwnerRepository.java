package org.pzsp2.proman.database_management.tables.owner.repository;

import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByLogin(String login);
}