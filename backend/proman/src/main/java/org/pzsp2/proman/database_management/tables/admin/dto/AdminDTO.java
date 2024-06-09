package org.pzsp2.proman.database_management.tables.admin.dto;

import org.pzsp2.proman.database_management.tables.admin.model.Admin;

public record AdminDTO(long adminId, String login, String password,
        String name, String surname, String address, String email, String phoneNumber) {
    public static AdminDTO of(final Admin admin) {
        return new AdminDTO(
            admin.getAdminId(),
            admin.getLogin(),
            admin.getPassword(),
            admin.getName(),
            admin.getSurname(),
            admin.getAddress(),
            admin.getEmail(),
            admin.getPhoneNumber()
        );
    }
}
