package com.example.Ecertificate.view;




import com.example.Ecertificate.models.User;
import com.example.Ecertificate.models.Role;
import com.example.Ecertificate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public List<User> getUser() {
        return userRepo.findAll();
    }


    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }


    public User addUser(User user) {
        // You might add password hashing logic here before saving
        return userRepo.save(user);
    }


    public User updateUser(Integer id, User userDetails) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            User u = optional.get();
            u.setName(userDetails.getName());
            u.setEmail(userDetails.getEmail());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                u.setPassword(userDetails.getPassword()); // Add hashing here
            }
            u.setRole(userDetails.getRole());
            return userRepo.save(u);
        }
        return null;
    }


    public String deleteUser(Integer id) {
        userRepo.deleteById(id);
        return "user with ID " + id + " is deleted";
    }


    public Role loginUser(String email, String password) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get().getRole();
        }
        throw new RuntimeException("Invalid credentials");
    }


    public List<User> getUserByName(String name) {
        return userRepo.findByName(name);
    }


    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }


    public List<User> getUserByRole(Role role){
        return userRepo.findByRole(role);
    }
}