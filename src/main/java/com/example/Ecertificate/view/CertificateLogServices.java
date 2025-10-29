package com.example.Ecertificate.view;



import com.example.Ecertificate.dto.LogRequestDTO;
import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.models.CertificateLog;
import com.example.Ecertificate.repository.CertificateLogRepo;
import com.example.Ecertificate.repository.CertificateRepo;
import com.example.Ecertificate.dto.LogRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateLogServices {

    @Autowired
    private CertificateLogRepo logRepo;

    @Autowired
    private CertificateRepo certificateRepo;

    public List<CertificateLog> getAllLogs() {
        return logRepo.findAll();
    }

    public List<CertificateLog> getLogsByUserId(Integer userId) {
        return logRepo.findByCertificateUserId(userId);
    }

    public CertificateLog addManualLog(LogRequestDTO logRequest) {
        // 1. Find the certificate to log against
        Certificate certificate = certificateRepo.findById(logRequest.getCertificateId())
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + logRequest.getCertificateId()));

        // 2. Create the new log
        CertificateLog newLog = new CertificateLog();
        newLog.setCertificate(certificate);
        newLog.setAction(logRequest.getAction());

        // 3. Save and return
        return logRepo.save(newLog);
    }
}