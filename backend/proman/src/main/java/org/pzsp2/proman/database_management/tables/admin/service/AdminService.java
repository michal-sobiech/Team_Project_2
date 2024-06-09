package org.pzsp2.proman.database_management.tables.admin.service;

import org.pzsp2.proman.database_management.tables.admin.dto.AdminDTO;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();

    Admin getAdminById(long adminId);

    void deleteAdminById(Long adminId);

    AdminDTO addNewAdmin(Admin adminToAdd);

    AdminDTO editAdmin(Admin adminToEdit);

    Admin findByLogin(String login);
}
