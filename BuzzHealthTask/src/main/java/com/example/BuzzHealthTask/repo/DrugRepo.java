package com.example.BuzzHealthTask.repo;

import com.example.BuzzHealthTask.entity.Drug;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DrugRepo extends JpaRepository<Drug, UUID> {
    boolean existsByNdc(@NotBlank String ndc);
}
