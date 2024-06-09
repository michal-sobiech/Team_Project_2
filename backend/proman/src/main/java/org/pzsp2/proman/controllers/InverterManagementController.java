package org.pzsp2.proman.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterService;
import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;
import org.pzsp2.proman.database_management.tables.inverter_model.service.InverterModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ADMIN", "OWNER"})
@RestController
@CrossOrigin
public class InverterManagementController {

    private final InverterService inverterService;
    private final InverterModelService inverterModelService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InverterManagementController(InverterService inverterService,
                                        InverterModelService inverterModelService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.inverterService = inverterService;
        this.inverterModelService = inverterModelService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @AllArgsConstructor
    @Getter
    private static class InvertersResponse {
        private String status;
        private String username;
        private String role;
        private List<InverterDTO> inverters;
    }

    @AllArgsConstructor
    @Getter
    private static class InverterCreateResponse {
        private String username;
        private String role;
        private List<InverterModel> inverterModels;
    }

    @AllArgsConstructor
    @Getter
    private static class InverterEditResponse {
        private String username;
        private String role;
        private InverterDTO inverter;
        private List<InverterModel> inverterModels;
    }

    @GetMapping("/inverters")
    public InvertersResponse InvertersPage() {
        System.out.println("Tried to access the inverters page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();

        System.out.println(username);

        return new InvertersResponse("success", username, role, inverterService.getAllInverters());
    }

    @GetMapping("/inverters/create")
    public ResponseEntity<InverterCreateResponse> InvertersCreatePage() {
        System.out.println("Tried to access the inverters create page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();
        List<InverterModel> inverterModels = inverterModelService.getAllInverterModels();
        System.out.println(username);

        InverterCreateResponse response =  new InverterCreateResponse(username, role, inverterModels);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/inverters/create")
    public ResponseEntity<?> createInverter(@RequestBody Inverter inverter) {
        System.out.println("Próba utworzenia inwertera o id: " + inverter.getInverterId());
        if (isInverterFieldsEmpty(inverter)) {
            return ResponseEntity.badRequest().body("All fields must be filled");
        }
        inverter.setPassword(bCryptPasswordEncoder.encode(inverter.getPassword()));
        InverterDTO addedInverter = inverterService.addNewInverter(inverter);
        if (addedInverter != null) {
            return ResponseEntity.ok(addedInverter);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit inverter");
        }
    }

    @DeleteMapping("/inverters/delete")
    public ResponseEntity<?> deleteInverter(@RequestParam("inverterId") long inverterId) {
        System.out.println("Próba usunięcia inwertera o id: " + inverterId);
        inverterService.deleteInverterById(inverterId);
        return new ResponseEntity<>("Inverter deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/inverters/edit")
    public ResponseEntity<InverterEditResponse> InvertersEditPage(@RequestParam("inverterId") long inverterId) {
        System.out.println("Tried to access the inverters page");
        System.out.println("Requested inverter ID: " + inverterId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();

        System.out.println(username);

        // Fetch data for the specific inverter using the inverterId
        InverterDTO inverter = inverterService.getInverterById(inverterId);
        List<InverterModel> inverterModels = inverterModelService.getAllInverterModels();

        if (inverter != null) {
            InverterEditResponse response = new InverterEditResponse(username, role, inverter, inverterModels);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            InverterEditResponse response = new InverterEditResponse("", "", null, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/inverters/edit")
    public ResponseEntity<?> editInverter(@RequestBody Inverter inverter) {
        System.out.println("Proba edycji inwertera o id: " + inverter.getInverterId());
        if (isInverterFieldsEmpty(inverter)) {
            return ResponseEntity.badRequest().body("All fields must be filled");
        }
        if (inverter.getPassword() == null || inverter.getPassword().isEmpty()){
            System.out.print("User Password empty ");
            inverter.setPassword("");
        }
        else{
            inverter.setPassword(bCryptPasswordEncoder.encode(inverter.getPassword()));
        }
        // Edit the inverter using the inverterService
        InverterDTO editedInverter = inverterService.editInverter(inverter);
        if (editedInverter != null) {
            return ResponseEntity.ok(editedInverter);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit inverter");
        }
    }

    private boolean isInverterFieldsEmpty(Inverter inverter) {
        return isNullOrEmpty(inverter.getCode()) || isNullOrEmpty(inverter.getModel()) || isNullOrEmpty(inverter.getIpAddress());
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isNullOrEmpty(Object value) {
        return value == null;
    }

}
