package org.pzsp2.proman.database_management.tables.user.model;

import java.util.Collection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "uzytkownik")
@Table(name = "uzytkownicy")
@Getter
@Setter
public class User implements UserDetails {
    @Id()
    @Column(name = "id_uzytkownika")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="login")
    private String username;

    @Column(name="haslo")
    private String password;

    @Column(name="imie")
    private String name;

    @Column(name="nazwisko")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="adres_zamieszkania")
    private String address;

    @Column(name="numer_telefonu")
    private String phoneNumber;

    @Column(name="id_uprawnienia")
    private long privilegesId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
//        this.registerDate = ZonedDateTime.now();
    }

    public User(String username, String password, String name, String surname, String email, String address,
                String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public User() { }

}
