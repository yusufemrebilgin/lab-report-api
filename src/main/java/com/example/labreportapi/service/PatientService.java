package com.example.labreportapi.service;

import com.example.labreportapi.dao.PatientRepository;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
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
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<ApiResponse<List<Patient>>> findAll() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ApiResponse.build(HttpStatus.OK, "The patient(s) found successfully", patients);
    }
    public ResponseEntity<ApiResponse<Patient>> findById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return ApiResponse.build(HttpStatus.OK, "The patient found successfully", patient);
        } else {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }
    }
    public ResponseEntity<ApiResponse<Patient>> add(Patient patient) {
        if (patient == null || patient.getFirstName() == null || patient.getLastName() == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Provided fields cannot be null");
        }

        PatientDetail patientDetail = patient.getPatientDetail();
        if (patientDetail != null) {
            patientDetail.setPatient(patient);
        }

        List<Report> reports = patient.getReports();
        if (reports != null) {
            for (Report r : reports)
                r.setPatient(patient);
        }

        patientRepository.save(patient);
        return ApiResponse.build(HttpStatus.CREATED, "The patient successfully created", patient);
    }

    public ResponseEntity<ApiResponse<Patient>> update(Patient updatedPatient, int id) {
        if (updatedPatient == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Patient cannot be null");
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient existingPatient = optionalPatient.get();
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());

            List<Report> updatedReportList = updatedPatient.getReports();
            if (updatedReportList != null && !updatedReportList.isEmpty()) {
                existingPatient.setReports(updatedReportList);
            }

            if (updatedPatient.getPatientDetail() != null) {
                existingPatient.setPatientDetail(updatedPatient.getPatientDetail());
            }

            updatedPatient = patientRepository.save(existingPatient);
            return ApiResponse.build(HttpStatus.OK, "Patient updated with id: " + id, updatedPatient);
        } else {
            return ApiResponse.build(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
        }
    }

    public ResponseEntity<ApiResponse<Void>> delete(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient existingPatient = optionalPatient.get();

            List<Report> reports = existingPatient.getReports();
            if (reports != null) {
                reports.forEach(report -> report = null);
            }

            patientRepository.delete(existingPatient);

            return ApiResponse.build(HttpStatus.OK, "Patient with id " + id + " deleted successfully");
        }
        return ApiResponse.build(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
    }

}
