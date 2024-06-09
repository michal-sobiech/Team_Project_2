package org.pzsp2.proman.database_management.tables.owner.controller;

import org.pzsp2.proman.database_management.tables.owner.dto.OwnerDTO;
import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.pzsp2.proman.database_management.tables.owner.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public List<OwnerDTO> getAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        return owners.stream()
                .map(this::mapOwnerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owners/{ownerId}")
    public OwnerDTO getOwner(@PathVariable long ownerId) {
        Owner owner = ownerService.getOwnerById(ownerId);
        return ownerService.mapToDTO(owner);
    }
// Tutaj można przypisać zarówno za pomocą mapOwnerToDTO jak i mapToDTO, nie udało mi się określić które rozwiązanie
// powinno zostać wykorzystane
    private OwnerDTO mapOwnerToDTO(Owner owner) {
        return new OwnerDTO(
                owner.getId(),
                owner.getName(),
                owner.getSurname(),
                owner.getLogin(),
                owner.getPassword()
        );
    }
}