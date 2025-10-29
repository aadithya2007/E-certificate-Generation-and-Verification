package com.example.Ecertificate.repository;

import com.example.Ecertificate.models.CertificateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateLogRepo extends JpaRepository<CertificateLog, Integer> {
    List<CertificateLog> findByCertificateUserId(Integer userId);
}