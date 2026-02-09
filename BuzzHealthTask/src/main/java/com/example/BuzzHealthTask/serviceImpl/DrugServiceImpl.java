package com.example.BuzzHealthTask.serviceImpl;

import com.example.BuzzHealthTask.entity.Drug;
import com.example.BuzzHealthTask.exception.BadRequestException;
import com.example.BuzzHealthTask.exception.ResourceNotFoundException;
import com.example.BuzzHealthTask.repo.DrugRepo;
import com.example.BuzzHealthTask.request.DrugRequest;
import com.example.BuzzHealthTask.request.DrugUpdateRequest;
import com.example.BuzzHealthTask.response.DrugResponse;
import com.example.BuzzHealthTask.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugRepo drugRepo;

    @Override
    @Transactional
    public DrugResponse create(DrugRequest request) {

        if (drugRepo.existsByNdc(request.ndc())) {
            throw new BadRequestException("Drug with NDC already exists");
        }

        Drug drug = new Drug();
        drug.setName(request.name());
        drug.setNdc(request.ndc());
        drug.setStrength(request.strength());
        drug.setForm(request.form());
        drug.setCreatedAt(LocalDateTime.now());

        return mapDto(drugRepo.save(drug));
    }

    @Override
    public DrugResponse getById(UUID id) {
        Drug drug = drugRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found with id: " + id));
        return mapDto(drug);
    }

    @Override
    public List<DrugResponse> getAll() {
        return drugRepo.findAll()
                .stream()
                .map(this::mapDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DrugResponse update(UUID id, DrugUpdateRequest request) {
        Drug drug = drugRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found with id: " + id));

        if (!drug.getNdc().equals(request.ndc()) && drugRepo.existsByNdc(request.ndc())) {
            throw new BadRequestException("Drug with NDC already exists");
        }

        drug.setName(request.name());
        drug.setNdc(request.ndc());
        drug.setStrength(request.strength());
        drug.setForm(request.form());

        return mapDto(drugRepo.save(drug));
    }

    @Override
    public void delete(UUID id) {
        if (!drugRepo.existsById(id)) {
            throw new ResourceNotFoundException("Drug not found with id: " + id);
        }
        drugRepo.deleteById(id);
    }

    private DrugResponse mapDto(Drug drug) {
        return new DrugResponse(
                drug.getId(),
                drug.getName(),
                drug.getNdc(),
                drug.getStrength(),
                drug.getForm(),
                drug.getCreatedAt()
        );
    }
}
