package com.example.Ecertificate.controller;




import com.example.Ecertificate.dto.LogRequestDTO;
import com.example.Ecertificate.models.CertificateLog;
import com.example.Ecertificate.view.CertificateLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class CertificateLogController {

    @Autowired
    private CertificateLogServices logService;

    @GetMapping
    public List<CertificateLog> getAllLogs() {
        return logService.getAllLogs();
    }
    @GetMapping("/user/{userId}")
    public List<CertificateLog> getLogsByUser(@PathVariable Integer userId) {
        return logService.getLogsByUserId(userId);
    }




}