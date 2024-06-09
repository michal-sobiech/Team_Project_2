package org.pzsp2.proman.database_management.tables.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity(name = "admin")
@Table(name = "administratorzy")
@AllArgsConstructor
@Getter
@Setter
public class Admin implements Serializable{
    @Id()
    @Column(name = "id_admina")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long adminId;

    @Column(name = "login")
    private String login;

    @Column(name = "haslo")
    private String password;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "adres_zamieszkania")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "numer_telefonu")
    private String phoneNumber;

    @Column(name="id_uprawnienia")
    private long privilegesId;

    public Admin() {

    }
}
