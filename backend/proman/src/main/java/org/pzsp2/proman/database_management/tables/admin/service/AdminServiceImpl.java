package org.pzsp2.proman.database_management.tables.admin.service;

import org.pzsp2.proman.database_management.tables.admin.dto.AdminDTO;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.pzsp2.proman.database_management.tables.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll().stream().toList();
    }

    public Admin getAdminById(long adminId) {
        return Objects.requireNonNull(adminRepository.findById(adminId).orElse(null));
    }

    public AdminDTO addNewAdmin(Admin adminToAdd) {
        adminRepository.save(adminToAdd);
        return AdminDTO.of(adminToAdd);
    }

    public AdminDTO editAdmin(Admin adminToEdit) {
        Admin existingAdmin = adminRepository.findById(adminToEdit.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin does not exist"));
        existingAdmin.setLogin(adminToEdit.getLogin());
        existingAdmin.setEmail(adminToEdit.getEmail());
        existingAdmin.setAddress(adminToEdit.getAddress());
        existingAdmin.setPhoneNumber(adminToEdit.getPhoneNumber());
        existingAdmin.setLogin(adminToEdit.getLogin());
        existingAdmin.setName(adminToEdit.getName());
        existingAdmin.setSurname(adminToEdit.getSurname());
        // Ensure that if no password is given, password stays as it was
        if (adminToEdit.getPassword() == null || adminToEdit.getPassword().isEmpty()) {
            adminToEdit.setPassword(existingAdmin.getPassword());
        }
        existingAdmin.setPassword(adminToEdit.getPassword());
        adminRepository.save(existingAdmin);
        return AdminDTO.of(existingAdmin);
    }

    public void deleteAdminById(Long adminId) {
        adminRepository.deleteById(adminId);
    }

    public Admin findByLogin(String login) {
        return adminRepository.findByLogin(login);
    }
}
