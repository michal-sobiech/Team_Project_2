package org.pzsp2.proman.database_management.tables.inverter_a_data.repository;

import org.pzsp2.proman.database_management.tables.inverter_a_data.model.InverterAData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InverterADataRepository extends JpaRepository<InverterAData, Long> {
    List<InverterAData> findByInverterId(long inverterId);
}
