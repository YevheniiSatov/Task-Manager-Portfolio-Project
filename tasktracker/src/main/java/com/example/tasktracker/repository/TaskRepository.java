/**
 * Repository interface for accessing Task entities from the database.
 * This interface extends JpaRepository to provide CRUD operations.
 * @Autor Yevhenii Shatov
 */

package com.example.tasktracker.repository;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}