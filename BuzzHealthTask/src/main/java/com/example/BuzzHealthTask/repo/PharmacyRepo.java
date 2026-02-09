package com.example.BuzzHealthTask.repo;

import com.example.BuzzHealthTask.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, UUID> {
    boolean existsByNpi(String npi);
}
