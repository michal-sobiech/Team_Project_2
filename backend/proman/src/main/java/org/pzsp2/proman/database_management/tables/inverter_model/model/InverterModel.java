package org.pzsp2.proman.database_management.tables.inverter_model.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity(name = "inverter_model")
@Table(name = "modele_inwerterow")
@AllArgsConstructor
@Getter
@Setter
public class InverterModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelu")
    private long inverterModelId;

    @Column(name = "nazwa_modelu")
    private String modelName;

    @Column(name = "producent")
    private String producer;

    public InverterModel() {}
}
