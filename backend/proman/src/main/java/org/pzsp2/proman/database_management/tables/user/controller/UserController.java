package org.pzsp2.proman.database_management.tables.user.controller;

import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.user.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserServiceImpl userDetailsService;

    public UserController(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @GetMapping("/users")
//    public List<UserDTO> getAllUsers() {
//        return userDetailsService.getAllUsers();
//    }

    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable long userId) {
        return userDetailsService.getUserById(userId);
    }

//    @PostMapping("/users/edit/{userId}")
//    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO) {
//        UserDTO editedUserDTO = userDetailsService.editUser(userDTO);
//        return new ResponseEntity<>(editedUserDTO, HttpStatus.OK);
//    }

    @DeleteMapping("/users/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userDetailsService.deleteUserById(userId);
    }
}