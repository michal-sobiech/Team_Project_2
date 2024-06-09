package org.pzsp2.proman.security;

import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.pzsp2.proman.database_management.tables.owner.service.OwnerServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.pzsp2.proman.database_management.tables.admin.service.AdminServiceImpl;
import org.pzsp2.proman.database_management.tables.user.service.UserServiceImpl;
import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import java.util.List;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserServiceImpl userServiceImpl;
    private final AdminServiceImpl adminServiceImpl;
    private final OwnerServiceImpl ownerServiceImpl;

    public UserDetailsServiceImpl(UserServiceImpl userServiceImpl,
            AdminServiceImpl adminServiceImpl, OwnerServiceImpl ownerServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.adminServiceImpl = adminServiceImpl;
        this.ownerServiceImpl = ownerServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        List<UserDTO> users = userServiceImpl.getAllUsers();
        for (UserDTO user : users) {
            if (user.username().equals(username)) {
                return User
                    .withUsername(username)
                    .password("{bcrypt}" + user.password())
                    .authorities("USER")
                    .build();
            }
        }
        List<Admin> admins = adminServiceImpl.getAllAdmins();
        for (Admin admin : admins) {
            System.out.println("aaa" + admin.getLogin() + username);
            if (admin.getLogin().equals(username)) {
                return User
                    .withUsername(username)
                    .password("{bcrypt}" + admin.getPassword())
                    .authorities("ADMIN")
                    .build();
            }
        }
        List<Owner> owners = ownerServiceImpl.getAllOwners();
        for (Owner owner : owners) {
            System.out.println("aaa" + owner.getLogin() + username);
            if (owner.getLogin().equals(username)) {
                return User
                        .withUsername(username)
                        .password("{bcrypt}" + owner.getPassword())
                        .authorities("OWNER")
                        .build();
            }
        }
        throw new UsernameNotFoundException(username);
    }

}
