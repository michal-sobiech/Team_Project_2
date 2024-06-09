package org.pzsp2.proman.database_management.tables.inverter_model.repository;

import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InverterModelRepository extends JpaRepository<InverterModel, Long> {
}