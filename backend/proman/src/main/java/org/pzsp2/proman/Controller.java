package org.pzsp2.proman;

import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.pzsp2.proman.database_management.tables.owner.service.OwnerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.pzsp2.proman.database_management.tables.admin.service.AdminServiceImpl;

@RestController
@CrossOrigin
public class Controller {

    private final AdminServiceImpl adminServiceImpl;
    private final OwnerService ownerService;

    public Controller(AdminServiceImpl adminServiceImpl,
            OwnerService ownerService) {
        this.adminServiceImpl = adminServiceImpl;
        this.ownerService = ownerService;
    }

    @AllArgsConstructor
    @Getter
    private static class HomePageResponse {
        private String status;
        private String name;
        private String role;
        private String surname;
    }


    @GetMapping("/home")
    public HomePageResponse homePage() {
        System.out.println("Tried to access the home page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();

        if (role.equals("admin")) {
            Admin admin = adminServiceImpl.findByLogin(username);
            String name = admin.getName();
            String surname = admin.getSurname();
            return new HomePageResponse(
                    "success",
                    name,
                    role,
                    surname);
        }
        if (role.equals("owner")) {
            Owner owner = ownerService.findByLogin(username);
            String name = owner.getName();
            String surname = owner.getSurname();
            return new HomePageResponse(
                    "success",
                    name,
                    role,
                    surname);
        }
        return null;
    }
}
