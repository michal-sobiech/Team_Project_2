package org.pzsp2.proman.database_management.tables.owner.dto;


import org.pzsp2.proman.database_management.tables.owner.model.Owner;

public record OwnerDTO(long id, String login, String password, String name, String surname) {
    public static OwnerDTO of(Owner owner) {
        return new OwnerDTO(
                owner.getId(),
                owner.getLogin(),
                owner.getPassword(),
                owner.getName(),
                owner.getSurname()
        );
    }
}