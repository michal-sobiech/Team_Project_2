package org.pzsp2.proman.database_management.tables.inverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;

import java.util.List;

@Repository
public interface InverterRepository extends JpaRepository<Inverter, Long> {
    List<Inverter> findByUserId(long userId);
}
