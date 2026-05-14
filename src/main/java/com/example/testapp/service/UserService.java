package com.example.testapp.service;

import com.example.testapp.entity.User;
import com.example.testapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.debug("getAllUsers - returned {} user(s)", users.size());
        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("getUserById - user not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
                });
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("createUser - email already in use: {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use: " + user.getEmail());
        }
        User saved = userRepository.save(user);
        logger.info("createUser - created user with id: {}", saved.getId());
        return saved;
    }

    public User updateUser(Long id, User updated) {
        User existing = getUserById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        User saved = userRepository.save(existing);
        logger.info("updateUser - updated user with id: {}", id);
        return saved;
    }

    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
        logger.info("deleteUser - deleted user with id: {}", id);
    }
}
