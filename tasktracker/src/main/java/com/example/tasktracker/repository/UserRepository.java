/**
 * Repository interface for accessing User entities from the database.
 * This interface extends JpaRepository to provide CRUD operations.
 * @Autor Yevhenii Shatov
 */

package com.example.tasktracker.repository;

import com.example.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
