package org.pzsp2.proman.database_management.tables.user.dto;

import org.pzsp2.proman.database_management.tables.user.model.User;

import java.io.Serializable;

public record UserDTO(Long userId, String username, String password, String name, String surname, String email,
                      String address, String phoneNumber) implements Serializable {
    public static UserDTO of(final User user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(),
                user.getEmail(), user.getAddress(), user.getPhoneNumber());
    }
}