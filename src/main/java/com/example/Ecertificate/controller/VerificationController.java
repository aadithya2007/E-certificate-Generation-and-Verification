package com.example.Ecertificate.controller;

import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.view.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/{certificateCode}")
    public ResponseEntity<Certificate> verifyCertificateByCode(@PathVariable UUID certificateCode) {
        try {
            Certificate certificate = verificationService.verifyByCode(certificateCode);
            return ResponseEntity.ok(certificate);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/qr/{certificateId}")
    public ResponseEntity<Certificate> verifyCertificateByQr(@PathVariable Integer certificateId) {
        try {
            Certificate certificate = verificationService.verifyByQrId(certificateId);
            return ResponseEntity.ok(certificate);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}