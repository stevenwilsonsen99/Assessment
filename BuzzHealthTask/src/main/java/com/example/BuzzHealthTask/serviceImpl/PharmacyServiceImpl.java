package com.example.BuzzHealthTask.serviceImpl;

import com.example.BuzzHealthTask.entity.Pharmacy;
import com.example.BuzzHealthTask.exception.BadRequestException;
import com.example.BuzzHealthTask.exception.ResourceNotFoundException;
import com.example.BuzzHealthTask.repo.PharmacyRepo;
import com.example.BuzzHealthTask.request.PharmacyRequest;
import com.example.BuzzHealthTask.response.PharmacyResponse;
import com.example.BuzzHealthTask.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepo repository;

    @Override
    @Transactional
    public PharmacyResponse create(PharmacyRequest request) {
        if (repository.existsByNpi(request.npi())) {
            throw new BadRequestException("Pharmacy with NPI already exists");
        }

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setNpi(request.npi());
        pharmacy.setName(request.name());
        pharmacy.setAddress(request.address());
        pharmacy.setCity(request.city());
        pharmacy.setState(request.state());
        pharmacy.setZip(request.zip());
        pharmacy.setCreatedAt(LocalDateTime.now());
        return map(repository.save(pharmacy));
    }

    @Override
    public PharmacyResponse getById(UUID id) {
        return repository.findById(id)
                .map(this::map)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found with id: " + id));
    }

    @Override
    public List<PharmacyResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PharmacyResponse update(UUID id, PharmacyRequest request) {
        Pharmacy pharmacy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found with id: " + id));

        if (!pharmacy.getNpi().equals(request.npi()) && repository.existsByNpi(request.npi())) {
            throw new BadRequestException("Pharmacy with NPI already exists");
        }

        pharmacy.setNpi(request.npi());
        pharmacy.setName(request.name());
        pharmacy.setAddress(request.address());
        pharmacy.setCity(request.city());
        pharmacy.setState(request.state());
        pharmacy.setZip(request.zip());

        return map(repository.save(pharmacy));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pharmacy not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private PharmacyResponse map(Pharmacy pharmacy) {
        return new PharmacyResponse(
                pharmacy.getId(),
                pharmacy.getNpi(),
                pharmacy.getName(),
                pharmacy.getCity(),
                pharmacy.getState(),
                pharmacy.getCreatedAt()
        );
    }
}
