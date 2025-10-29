package com.example.Ecertificate.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @Column(name = "certificate_title")
    private String certificateTitle;

    @Lob
    private String details;

    @Lob
    @Column(name = "event_details")
    private String eventDetails;

    @Column(name = "signature_path")
    private String signaturePath;

    @Column(name = "verification_code", unique = true, nullable = false)
    private UUID verificationCode;

    @Column(name = "issued_date")
    private LocalDate issuedDate;
}