package com.example.Ecertificate.repository;

import com.example.Ecertificate.models.VerificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRecordRepo extends JpaRepository<VerificationRecord, Integer> {
}