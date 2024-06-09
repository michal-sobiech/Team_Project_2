package org.pzsp2.proman.database_management.tables.user.repository;

import org.pzsp2.proman.database_management.tables.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

