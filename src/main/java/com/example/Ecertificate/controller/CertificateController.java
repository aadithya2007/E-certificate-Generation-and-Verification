package com.example.Ecertificate.controller;




import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.view.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.createCertificateTemplate(certificate);
    }

    @GetMapping
    public List<Certificate> getAllCertificates() {
        return certificateService.getAllCertificates();
    }
    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable Integer id) {
        return certificateService.getCertificateById(id);
    }






}