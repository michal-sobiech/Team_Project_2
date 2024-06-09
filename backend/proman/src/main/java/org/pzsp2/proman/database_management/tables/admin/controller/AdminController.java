package org.pzsp2.proman.database_management.tables.admin.controller;

import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.pzsp2.proman.database_management.tables.admin.service.AdminServiceImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final AdminServiceImpl adminDetailsService;

    public AdminController(AdminServiceImpl adminDetailsService) {
        this.adminDetailsService = adminDetailsService;
    }

    @GetMapping("/admins/{adminId}")
    public Admin getAdmin(@PathVariable long adminId) {
        return adminDetailsService.getAdminById(adminId);
    }

    @DeleteMapping("/admins/delete/{adminId}")
    public void deleteAdmin(@PathVariable Long adminId) {
        adminDetailsService.deleteAdminById(adminId);
    }
}