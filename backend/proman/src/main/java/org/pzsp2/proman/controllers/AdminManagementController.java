package org.pzsp2.proman.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pzsp2.proman.database_management.tables.admin.dto.AdminDTO;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.pzsp2.proman.database_management.tables.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("OWNER")
@RestController
@CrossOrigin
public class AdminManagementController {

    private final AdminService adminService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminManagementController(AdminService adminService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminService = adminService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @AllArgsConstructor
    @Getter
    private static class AdminsResponse {
        private String username;
        private String role;
        private List<Admin> admins;
    }

    @AllArgsConstructor
    @Getter
    private static class AdminsCreateResponse {
        private String username;
        private String role;
    }

    @AllArgsConstructor
    @Getter
    private static class AdminsEditResponse {
        private String username;
        private String role;
        private Admin admin;
    }
    @GetMapping("/admins")
    public ResponseEntity<AdminsResponse> AdminsPage() {
        System.out.println("Tried to access the admins page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();
        AdminsResponse response = new AdminsResponse(username, role, adminService.getAllAdmins());
        System.out.println(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admins/create")
    public ResponseEntity<AdminsCreateResponse> AdminCreate() {
        System.out.println("Tried to access the admins page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String role = (String) authentication.getPrincipal();
        AdminsCreateResponse response = new AdminsCreateResponse(login, role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admins/create")
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody Admin adminToAdd) {
        adminToAdd.setPrivilegesId(2);  // Set to normal Admin privileges, field is here to ensure extensibility
        adminToAdd.setPassword(bCryptPasswordEncoder.encode(adminToAdd.getPassword()));
        AdminDTO createdAdminDTO = adminService.addNewAdmin(adminToAdd);
        return new ResponseEntity<>(createdAdminDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admins/delete")
    public ResponseEntity<String> deleteAdmin(@RequestParam("adminId") long adminId) {
        System.out.println("Próba usunięcia inwertera o id: " + adminId);
        adminService.deleteAdminById(adminId);
        return new ResponseEntity<>("Admin deleted sucessfully", HttpStatus.OK);
    }

    @GetMapping("/admins/edit")
    public ResponseEntity<AdminsEditResponse> AdminsEdit(@RequestParam("adminId") long adminId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String role = (String) authentication.getPrincipal();
        System.out.println(login);
        // Fetch data for the specific admin using their adminId
        Admin admin = adminService.getAdminById(adminId);
        if (admin != null) {
            AdminsEditResponse response = new AdminsEditResponse(login, role, admin);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            AdminsEditResponse response = new AdminsEditResponse("", "", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admins/edit")
    public ResponseEntity<?> editAdmin(@RequestBody Admin admin) {
        if (isAdminFieldsEmpty(admin)) {
            return ResponseEntity.badRequest().body("All fields must be filled");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()){
            System.out.print("Admin Password empty ");
            admin.setPassword("");
        }
        else{
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        }
        System.out.println("Admin Password after if else :" + admin.getPassword());
        AdminDTO editedAdmin = adminService.editAdmin(admin);
        if (editedAdmin != null) {
            return ResponseEntity.ok(editedAdmin);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit admin");
        }
    }

    private boolean isAdminFieldsEmpty(Admin admin) {
        return isNullOrEmpty(admin.getName()) ||
                isNullOrEmpty(admin.getSurname()) ||
                isNullOrEmpty(admin.getLogin()) ||
                isNullOrEmpty(admin.getEmail()) ||
                isNullOrEmpty(admin.getAddress()) ||
                isNullOrEmpty(admin.getPhoneNumber());
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }


}