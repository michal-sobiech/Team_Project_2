package org.pzsp2.proman.database_management.tables.owner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity(name = "owner")
@Table(name = "wlasciciele")
@AllArgsConstructor
@Getter
@Setter
public class Owner implements Serializable{
    @Id
    @Column(name = "id_wlasciciela")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long id;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "login")
    private String login;

    @Column(name = "haslo")
    private String password;

    @Column(name="id_uprawnienia")
    private long privilegesId;

    public Owner() {

    }
}
