package org.pzsp2.proman.database_management.tables.user.service;

import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.user.model.User;
import org.pzsp2.proman.database_management.tables.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::of).toList();
    }

    public UserDTO getUserById(long userId) {
        return UserDTO.of(Objects.requireNonNull(userRepository.findById(userId).orElse(null)));
    }

    public UserDTO addNewUser(User userToAdd) {
        userRepository.save(userToAdd);
        return UserDTO.of(userToAdd);
    }

    public UserDTO editUser(User userToEdit) {
        User existingUser = userRepository.findById(userToEdit.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        existingUser.setUsername(userToEdit.getUsername());
        existingUser.setEmail(userToEdit.getEmail());
        existingUser.setAddress(userToEdit.getAddress());
        existingUser.setPhoneNumber(userToEdit.getPhoneNumber());
        existingUser.setUsername(userToEdit.getUsername());
        existingUser.setName(userToEdit.getName());
        existingUser.setSurname(userToEdit.getSurname());
        // Ensure that if no password is given, password stays as it was
        if (userToEdit.getPassword() == null || userToEdit.getPassword().isEmpty()) {
            userToEdit.setPassword(existingUser.getPassword());
        }
        existingUser.setPassword(userToEdit.getPassword());
        userRepository.save(existingUser);
        return UserDTO.of(existingUser);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    public long getUserId(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get().getUserId();
        }
        return -1;
    }
}
