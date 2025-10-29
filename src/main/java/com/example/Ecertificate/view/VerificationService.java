package com.example.Ecertificate.view;

import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.models.VerificationRecord;
import com.example.Ecertificate.repository.CertificateRepo;
import com.example.Ecertificate.repository.VerificationRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationService {

    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private VerificationRecordRepo verificationRepo;


    public Certificate verifyByCode(UUID certificateCode) {
        Certificate certificate = certificateRepo.findByVerificationCode(certificateCode)
                .orElseThrow(() -> new RuntimeException("Verification Failed: Invalid Code."));

        logVerification(certificate, "VERIFIED_BY_CODE");

        return certificate;
    }

    public Certificate verifyByQrId(Integer certificateId) {

        Certificate certificate = certificateRepo.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Verification Failed: Invalid QR Code (ID not found)."));
        logVerification(certificate, "VERIFIED_BY_QR");
        return certificate;
    }


    private void logVerification(Certificate certificate, String status) {
        VerificationRecord record = new VerificationRecord();
        record.setCertificate(certificate);
        record.setStatus(status);
        verificationRepo.save(record);
    }
}