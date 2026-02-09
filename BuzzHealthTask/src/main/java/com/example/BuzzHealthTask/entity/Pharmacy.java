package com.example.BuzzHealthTask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pharmacy")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pharmacy {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String npi;

    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "pharmacy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coupon> coupons;

}

