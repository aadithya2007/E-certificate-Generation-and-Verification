package com.example.Ecertificate.controller;

import com.example.Ecertificate.dto.LogRequestDTO;
import com.example.Ecertificate.models.CertificateLog;
import com.example.Ecertificate.models.Role;
import com.example.Ecertificate.view.CertificateLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class CertificateLogController {

    @Autowired
    private CertificateLogServices logService;

    @GetMapping
    public List<CertificateLog> getAllLogs(
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return logService.getAllLogs();
    }

    @GetMapping("/user/{userId}")
    public List<CertificateLog> getLogsByUser(
            @PathVariable Integer userId,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return logService.getLogsByUserId(userId);
    }

    @PostMapping
    public CertificateLog addLogEntry(
            @RequestBody LogRequestDTO logRequest,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return logService.addManualLog(logRequest);
    }
}