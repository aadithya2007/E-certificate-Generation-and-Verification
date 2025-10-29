package com.example.Ecertificate.view;


import com.example.Ecertificate.models.Certificate;
import com.example.Ecertificate.models.CertificateLog;
import com.example.Ecertificate.models.User;
import com.example.Ecertificate.repository.CertificateLogRepo;
import com.example.Ecertificate.repository.CertificateRepo;
import com.example.Ecertificate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CertificateLogRepo logRepo;


    public Certificate createCertificateTemplate(Certificate certificate) {

        certificate.setUser(null);
        certificate.setVerificationCode(null);
        certificate.setIssuedDate(null);
        return certificateRepo.save(certificate);
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepo.findAll();
    }


    public Certificate getCertificateById(Integer id) {
        return certificateRepo.findById(id).orElse(null);
    }

    public Certificate assignCertificate(Integer userId, Integer certificateTemplateId) {
        Certificate template = certificateRepo.findById(certificateTemplateId)
                .orElseThrow(() -> new RuntimeException("Certificate template not found with id: " + certificateTemplateId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Certificate assignedCertificate = new Certificate();
        assignedCertificate.setName(template.getName());
        assignedCertificate.setCertificateTitle(template.getCertificateTitle());
        assignedCertificate.setDetails(template.getDetails());
        assignedCertificate.setEventDetails(template.getEventDetails());
        assignedCertificate.setSignaturePath(template.getSignaturePath());

        assignedCertificate.setUser(user);
        assignedCertificate.setIssuedDate(LocalDate.now());
        assignedCertificate.setVerificationCode(UUID.randomUUID());


        Certificate savedCertificate = certificateRepo.save(assignedCertificate);


        CertificateLog log = new CertificateLog();
        log.setCertificate(savedCertificate);
        log.setAction("ASSIGNED - Certificate template " + certificateTemplateId + " assigned to user " + user.getName());
        logRepo.save(log);


        return savedCertificate;
    }


    public Resource downloadCertificatePdf(Integer certificateId) {

        Certificate cert = certificateRepo.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + certificateId));


        String userName = (cert.getUser() != null) ? cert.getUser().getName() : "N/A";


        String content = "--- CERTIFICATE OF COMPLETION ---\n\n"
                + "This certificate is awarded to: " + userName + "\n"
                + "For the course: " + cert.getCertificateTitle() + "\n"
                + "Event: " + cert.getEventDetails() + "\n\n"
                + "Issued on: " + cert.getIssuedDate() + "\n"
                + "Verification Code: " + cert.getVerificationCode();

        byte[] data = content.getBytes();
        return new ByteArrayResource(data);
    }
}