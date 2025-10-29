package com.example.Ecertificate.repository;


import com.example.Ecertificate.models.User;
import com.example.Ecertificate.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByName(String name);

    List<User> findByRole(Role role);
}
