package com.example.Ecertificate.controller;

import com.example.Ecertificate.models.Role;
import com.example.Ecertificate.models.User;
import com.example.Ecertificate.view.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    protected UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public Role loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            return userService.loginUser(email, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @GetMapping
    public List<User> getAllUsers(@RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.getUser();
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Integer id,
            @RequestBody User userDetails,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.updateUser(id, userDetails);
    }
}