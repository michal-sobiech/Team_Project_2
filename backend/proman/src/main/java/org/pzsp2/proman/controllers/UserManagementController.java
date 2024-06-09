package org.pzsp2.proman.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.user.model.User;
import org.pzsp2.proman.database_management.tables.user.service.UserService;
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
public class UserManagementController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManagementController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @AllArgsConstructor
    @Getter
    private static class UsersResponse {
        private String username;
        private String role;
        private List<UserDTO> users;
    }

    @AllArgsConstructor
    @Getter
    private static class UsersCreateResponse {
        private String username;
        private String role;
    }

    @AllArgsConstructor
    @Getter
    private static class UsersEditResponse {
        private String username;
        private String role;
        private UserDTO user;
    }
    @GetMapping("/users")
    public ResponseEntity<UsersResponse> UsersPage() {
        System.out.println("Tried to access the users page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();
        UsersResponse response = new UsersResponse(username, role, userService.getAllUsers());
        System.out.println(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/create")
    public ResponseEntity<UsersCreateResponse> UserCreate() {
        System.out.println("Tried to access the users page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();
        UsersCreateResponse response = new UsersCreateResponse(username, role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userToAdd) {
        userToAdd.setPrivilegesId(1);  // Set to normal User privileges, field is here to ensure extensibility
        userToAdd.setPassword(bCryptPasswordEncoder.encode(userToAdd.getPassword()));
        userService.addNewUser(userToAdd);
        UserDTO createdUserDTO = UserDTO.of(userToAdd);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") long userId) {
        System.out.println("Próba usunięcia inwertera o id: " + userId);
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User deleted sucessfully", HttpStatus.OK);
    }

    @GetMapping("/users/edit")
    public ResponseEntity<UsersEditResponse> UsersEdit(@RequestParam("userId") long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = (String) authentication.getPrincipal();
        System.out.println(username);
        // Fetch data for the specific user using their userId
        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            UsersEditResponse response = new UsersEditResponse(username, role, user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            UsersEditResponse response = new UsersEditResponse("", "", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/edit")
    public ResponseEntity<?> editUser(@RequestBody User user) {
        if (isUserFieldsEmpty(user)) {
            return ResponseEntity.badRequest().body("All fields must be filled");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            System.out.print("User Password empty ");
            user.setPassword("");
        }
        else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        System.out.println("User Password after if else :" + user.getPassword());
        UserDTO editedUser = userService.editUser(user);
        if (editedUser != null) {
            return ResponseEntity.ok(editedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit user");
        }
    }

    private boolean isUserFieldsEmpty(User user) {
        return isNullOrEmpty(user.getName()) ||
                isNullOrEmpty(user.getSurname()) ||
                isNullOrEmpty(user.getUsername()) ||
                isNullOrEmpty(user.getEmail()) ||
                isNullOrEmpty(user.getAddress()) ||
                isNullOrEmpty(user.getPhoneNumber());
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}