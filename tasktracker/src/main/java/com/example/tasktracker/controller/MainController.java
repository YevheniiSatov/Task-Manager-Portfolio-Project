/**
 * Controller class for handling main application routes.
 * This class provides routes for home, login, registration, and task management.
 * @Autor Yevhenii Shatov
 */

package com.example.tasktracker.controller;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.User;
import com.example.tasktracker.service.TaskService;
import com.example.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    /**
     * Handler for the home page.
     * @return The name of the home view
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Handler for the login page.
     * @return The name of the login view
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handler for the registration form.
     * @param model Model object
     * @return The name of the registration view
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Handler for user registration.
     * @param user User object
     * @param bindingResult BindingResult object
     * @param model Model object
     * @return The name of the view to redirect to
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!userService.isPasswordStrong(user.getPassword())) {
            model.addAttribute("passwordError", "Password is too weak. It must be at least 8 characters long and include a mix of upper and lower case letters, numbers, and special characters.");
            return "register";
        }

        userService.save(user);
        return "redirect:/login";
    }

    /**
     * Handler for displaying the list of tasks.
     * @param model Model object
     * @return The name of the tasks view
     */
    @GetMapping("/tasks")
    public String listTasks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("tasks", taskService.findByUser(user));
        return "tasks";
    }

    /**
     * Handler for displaying the task creation form.
     * @param model Model object
     * @return The name of the task form view
     */
    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    /**
     * Handler for saving a new task.
     * @param task Task object
     * @param bindingResult BindingResult object
     * @return The name of the view to redirect to
     */
    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "taskForm";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }

    /**
     * Handler for displaying the task editing form.
     * @param id Task ID
     * @param model Model object
     * @return The name of the task form view
     */
    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "taskForm";
    }

    /**
     * Handler for updating an existing task.
     * @param id Task ID
     * @param task Task object
     * @param bindingResult BindingResult object
     * @return The name of the view to redirect to
     */
    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute("task") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "taskForm";
        }

        Task existingTask = taskService.findById(id);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDeadline(task.getDeadline());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        taskService.save(existingTask);
        return "redirect:/tasks";
    }

    /**
     * Handler for deleting a task.
     * @param id Task ID
     * @return The name of the view to redirect to
     */
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}
