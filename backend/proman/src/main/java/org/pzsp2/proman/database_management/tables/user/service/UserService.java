package org.pzsp2.proman.database_management.tables.user.service;

import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.user.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(long userId);

    UserDTO addNewUser(User userToAdd);

    UserDTO editUser(User userDTO);

    void deleteUserById(long userId);

    long getUserId(String username);
}
