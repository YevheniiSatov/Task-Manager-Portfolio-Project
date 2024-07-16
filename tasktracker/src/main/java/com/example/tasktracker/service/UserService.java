/**
 * Service class for managing users.
 * This class provides methods to save, find, and validate users.
 * @Autor Yevhenii Shatov
 */

package com.example.tasktracker.service;

import com.example.tasktracker.model.User;
import com.example.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a user to the database.
     * @param user User instance
     */
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Finds a user by email.
     * @param email User email
     * @return User instance
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Checks if a password is strong.
     * @param password Password string
     * @return true if password is strong, false otherwise
     */
    public boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }

        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return false;
        }

        return true;
    }

    /**
     * Updates a user in the database.
     * @param user User instance
     */
    public void update(User user) {
        userRepository.save(user);
    }
}
