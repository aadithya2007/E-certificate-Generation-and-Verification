package com.example.Ecertificate.controller;

import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.models.Role;
import com.example.Ecertificate.models.User;
import com.example.Ecertificate.repository.UserRepo;
import com.example.Ecertificate.view.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    UserRepo userRepo;

    @PostMapping
    public Certificate createCertificate(
            @RequestBody Certificate certificate,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return certificateService.createCertificateTemplate(certificate);
    }


    @GetMapping
    public List<Certificate> getAllCertificates(
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole == Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Students cannot view all certificates");
        }
        return certificateService.getAllCertificates();
    }


    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable Integer id,
        @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole){
           ;
            if (userRole != Role.ADMIN && userRole != Role.FACULTY) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
            }
        return certificateService.getCertificateById(id);
    }


    @GetMapping("/my")
    public List<Certificate> getMyCertificates(
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader("X-User-Id") Integer userId) {
        System.out.print(userId);
        if (userRole != Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only students can access their certificates this way.");
        }
        return certificateService.getCertificatesForUser(userId);
    }



    @PostMapping("/assign/{userId}")
    public Certificate assignCertificate(
            @PathVariable Integer userId,
            @RequestParam Integer certificateId,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN && userRole != Role.FACULTY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if(user.getRole()!=Role.STUDENT){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Certificate cant be assigned to a faculty or admin");
        }
        return certificateService.assignCertificate(userId, certificateId);
    }

    @DeleteMapping("/{id}")
    public String deleteCertificate(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", defaultValue = "STUDENT") Role userRole) {
        if (userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return certificateService.deleteCertificate(id);
    }

    @GetMapping("/download/{certificateId}")
    public ResponseEntity<Resource> downloadCertificatePdf(@PathVariable Integer certificateId) {
        try {
            Resource resource = certificateService.downloadCertificatePdf(certificateId);
            String filename = "certificate-" + certificateId + ".txt";
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}