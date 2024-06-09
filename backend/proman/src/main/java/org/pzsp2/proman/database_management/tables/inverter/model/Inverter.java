package org.pzsp2.proman.database_management.tables.inverter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;

@Entity(name = "inverter")
@Table(name = "inwertery_uzytkownika")
@AllArgsConstructor
@Getter
@Setter
public class Inverter implements Serializable {
    @Id
    @Column(name="id_inwertera")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inverterId;

    @Column(name="kod_identyfikujacy")
    private String code;

    @Column(name="login")
    private String login;

    @Column(name="haslo")
    private String password;

    @Column(name="ip")
    private String ipAddress;

    // @Column(name="id_modelu")
    @ManyToOne
    @JoinColumn(name="id_modelu", referencedColumnName="id_modelu")
    private InverterModel model;

    @Column(name="id_uzytkownika")
    private long userId;

    public Inverter() { }

    public Inverter(long userId, String ipAddress) {
        this.userId = userId;
        this.ipAddress = ipAddress;
    }
}
