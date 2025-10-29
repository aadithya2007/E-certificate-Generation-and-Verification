package com.example.Ecertificate.repository;



import com.example.Ecertificate.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepo extends JpaRepository<Certificate, Integer> {

    Optional<Certificate> findByVerificationCode(UUID verificationCode);
}