package com.example.testapp.controller;

import com.example.testapp.entity.User;
import com.example.testapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("GET /api/users - fetching all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.info("GET /api/users/{} - fetching user by id", id);
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.info("POST /api/users - creating user with email: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        logger.info("PUT /api/users/{} - updating user", id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/{} - deleting user", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * @return String
     */
    @GetMapping("/checkUserExeception")
    public ResponseEntity<String> checkUserExeception() {
        logger.info("GET /api/checkUserExeception - check User Exeception");
        // Initialise the map so that get() does not throw a NullPointerException.
        Map<String, String> testmap = new HashMap<>();

        try {
            logger.info("checking testmap value: {}", testmap.get("testKey"));
        } catch (Exception e) {
            // Pass the exception as the Throwable cause (no placeholder) so that
            // SLF4J prints the full stack trace instead of just e.toString().
            logger.error("Exception in checkUserExeception", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("API called");
    }

}
