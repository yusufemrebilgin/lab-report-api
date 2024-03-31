package com.example.labreportapi.service;

import com.example.labreportapi.dao.LabTechnicianRepository;
import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.response.ApiResponse;
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

    public ResponseEntity<ApiResponse<List<LabTechnician>>> findAll() {
        List<LabTechnician> labTechnicians = labTechnicianRepository.findAll();
        if (labTechnicians.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ApiResponse.build(HttpStatus.OK, "The lab tech(s) found successfully", labTechnicians);
    }

    public ResponseEntity<ApiResponse<LabTechnician>> findById(int id) {
        Optional<LabTechnician> optionalLabTechnician = labTechnicianRepository.findById(id);
        if (optionalLabTechnician.isPresent()) {
            LabTechnician labTechnician = optionalLabTechnician.get();
            return ApiResponse.build(HttpStatus.OK, "Lab technician found successfully", labTechnician);
        } else {
            throw new EntityNotFoundException("Lab technician not found with id: " + id);
        }
    }
    public ResponseEntity<ApiResponse<LabTechnician>> add(LabTechnician labTechnician) {
        if (labTechnician == null || labTechnician.getHospitalId() == 0) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Lab technician or id cannot be empty");
        }

        List<Report> reports = labTechnician.getReports();
        if (reports != null) {
            reports.forEach(report -> report.setLabTechnician(labTechnician));
        }

        labTechnicianRepository.save(labTechnician);
        return ApiResponse.build(HttpStatus.CREATED, "The lab technician successfully created", labTechnician);
    }

    public ResponseEntity<ApiResponse<LabTechnician>> update(LabTechnician updatedTechnician, int id) {
        if (updatedTechnician == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Lab technician cannot be null");
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
            return ApiResponse.build(HttpStatus.OK, "Lab technician updated with id: " + id, updatedTechnician);
        } else {
            return ApiResponse.build(HttpStatus.NOT_FOUND, "Lab technician not found with id: " + id);
        }
    }

    public ResponseEntity<ApiResponse<Void>> delete(int id) {
        Optional<LabTechnician> optionalTechnician = labTechnicianRepository.findById(id);
        if (optionalTechnician.isPresent()) {
            LabTechnician existingTechnician = optionalTechnician.get();

            List<Report> reports = existingTechnician.getReports();
            if (reports != null) {
                reports.forEach(report -> report = null);
            }

            labTechnicianRepository.delete(existingTechnician);
            return ApiResponse.build(HttpStatus.OK, "Lab technician with id " + id + " deleted successfully");
        }
        return ApiResponse.build(HttpStatus.NOT_FOUND, "Lab technician not found with id: " + id);
    }

}
