package org.pzsp2.proman.database_management.tables.privileges.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity(name = "privileges")
@Table(name = "uprawnienia")
@AllArgsConstructor
@Getter
@Setter
public class Privileges implements Serializable{
    @Id
    @Column(name="id_uprawnienia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "opis")
    private String description;

    public Privileges() {
    }
}
