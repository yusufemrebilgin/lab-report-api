package com.example.labreportapi.service;

import com.example.labreportapi.dao.LabTechnicianRepository;
import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.entity.Report;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTechnicianService {

    private final LabTechnicianRepository labTechnicianRepository;

    @Autowired
    public LabTechnicianService(LabTechnicianRepository labTechnicianRepository) {
        this.labTechnicianRepository = labTechnicianRepository;
    }

    public ResponseEntity<List<LabTechnician>> findAll() {
        List<LabTechnician> labTechnicians = labTechnicianRepository.findAll();
        if (labTechnicians.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(labTechnicians);
    }

    public ResponseEntity<LabTechnician> findById(int id) {
        Optional<LabTechnician> optionalLabTechnician = labTechnicianRepository.findById(id);
        if (optionalLabTechnician.isPresent()) {
            LabTechnician labTechnician = optionalLabTechnician.get();
            return ResponseEntity.ok(labTechnician);
        } else {
            throw new EntityNotFoundException("Lab technician not found with id: " + id);
        }
    }

    public ResponseEntity<?> findByFirstNameAndLastName(String firstName, String lastName) {
        Optional<LabTechnician> optionalTechnician = labTechnicianRepository
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);

        if (optionalTechnician.isPresent()) {
            LabTechnician labTech = optionalTechnician.get();
            return ResponseEntity.ok(labTech);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab tech not found: " + firstName + " " + lastName);
    }

    public ResponseEntity<?> findByHospitalId(int hospitalId) {
        LabTechnician labTechnician = labTechnicianRepository.findByHospitalId(hospitalId);
        if (labTechnician == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab tech not found");
        }
        return ResponseEntity.ok(labTechnician);
    }

    public ResponseEntity<?> add(LabTechnician labTechnician) {
        if (labTechnician == null || labTechnician.getHospitalId() == 0) {
            return ResponseEntity.badRequest().body("Lab technician or id cannot be empty");
        }

        List<Report> reports = labTechnician.getReports();
        if (reports != null) {
            reports.forEach(report -> report.setLabTechnician(labTechnician));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(labTechnicianRepository.save(labTechnician));
    }

    public ResponseEntity<?> update(LabTechnician updatedTechnician, int id) {
        if (updatedTechnician == null) {
            return ResponseEntity.badRequest().body("Lab technician cannot be null");
        }

        Optional<LabTechnician> optionalTechnician = labTechnicianRepository.findById(id);
        if (optionalTechnician.isPresent()) {
            LabTechnician existingTechnician = optionalTechnician.get();
            existingTechnician.setFirstName(updatedTechnician.getFirstName());
            existingTechnician.setLastName(updatedTechnician.getLastName());

            List<Report> newReportList = existingTechnician.getReports();
            if (newReportList != null && !newReportList.isEmpty()) {
                existingTechnician.setReports(newReportList);
            }

            updatedTechnician = labTechnicianRepository.save(existingTechnician);
            return ResponseEntity.ok(updatedTechnician);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab technician not found with id: " + id);
        }
    }

    public ResponseEntity<String> delete(int id) {
        Optional<LabTechnician> optionalTechnician = labTechnicianRepository.findById(id);
        if (optionalTechnician.isPresent()) {
            LabTechnician existingTechnician = optionalTechnician.get();

            List<Report> reports = existingTechnician.getReports();
            if (reports != null) {
                reports.forEach(report -> report = null);
            }

            labTechnicianRepository.delete(existingTechnician);
            return ResponseEntity.ok("Lab technician with id " + id + " deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab tech not found with id: " + id);
    }

}
