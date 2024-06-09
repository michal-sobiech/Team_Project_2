package org.pzsp2.proman.database_management.tables.inverter.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class InverterController {

    private final InverterServiceImpl inverterService;

    public InverterController(InverterServiceImpl inverterService) {
        this.inverterService = inverterService;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class InvertersResponse {
        private String status;
        private String username;
        private String role;
        private ArrayList<InverterDTO> inverters;
    }

    // powinno byc samo /inverters, do zmiany pozniej
    @GetMapping("/admin_home/inverters")
    public InvertersResponse getAllInverters() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(username);

        System.out.println("trying to access inverters page");
        return new InvertersResponse("success", username,  "admin", new ArrayList<>(inverterService.getAllInverters()));
    }

    @GetMapping("/inverters/{inverterId}")
    public InverterDTO getInverter(@PathVariable Long inverterId) {
        return inverterService.getInverterById(inverterId);
    }

    // tak samo, jak wyzej do zmiany pozniej
    @PostMapping("/admin_home/inverters/create")
    public ResponseEntity<InverterDTO> createInverter(@RequestBody Inverter inverterToAdd) {
        System.out.println("tworze invertera w kontrolerze");
        inverterService.addNewInverter(inverterToAdd);
        InverterDTO createdInverterDTO = InverterDTO.of(inverterToAdd);
        return new ResponseEntity<>(createdInverterDTO, HttpStatus.CREATED);
    }

    @PostMapping("/inverters/edit/{inverterId}")
    public ResponseEntity<InverterDTO> editInverter(@RequestBody Inverter inverterToEdit) {
        inverterService.editInverter(inverterToEdit);
        InverterDTO editedInverterDTO = InverterDTO.of(inverterToEdit);
        return new ResponseEntity<>(editedInverterDTO, HttpStatus.OK);
    }

    @DeleteMapping("/inverters/delete/{inverterId}")
    public void deleteInverter(@PathVariable Long inverterId) {
        inverterService.deleteInverterById(inverterId);
    }
}