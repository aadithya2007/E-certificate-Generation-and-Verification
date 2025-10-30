package com.example.Ecertificate.dto;

import com.example.Ecertificate.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private Integer id;
    private Role role;
}