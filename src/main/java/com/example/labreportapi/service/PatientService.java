package com.example.labreportapi.service;

import com.example.labreportapi.dao.PatientRepository;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
import com.example.labreportapi.entity.Report;
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

    public ResponseEntity<List<Patient>> findAll() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }

    public ResponseEntity<List<Patient>> findAllByOrderFullNameAsc() {
        List<Patient> patients = patientRepository.findAllByOrderByFirstNameAscLastNameAsc();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }

    public ResponseEntity<Patient> findById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return ResponseEntity.ok(patient);
        } else {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }
    }

    public ResponseEntity<?> findByFirstNameAndLastName(String firstName, String lastName) {
        Optional<Patient> optionalPatient = patientRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        if (optionalPatient.isPresent()) {
            return ResponseEntity.ok(optionalPatient.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found: " + firstName + " " + lastName);
    }

    public ResponseEntity<?> findByIdentityNumber(long identityNumber) {
        Patient patient = patientRepository.findByPatientDetailIdentityNumber(identityNumber);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        return ResponseEntity.ok(patient);
    }

    public ResponseEntity<?> add(Patient patient) {
        if (patient == null || patient.getFirstName() == null || patient.getLastName() == null) {
            return ResponseEntity.badRequest().body("Provided fields cannot be null");
        }
        PatientDetail patientDetail = patient.getPatientDetail();
        List<Report> reports = patient.getReports();

        if (patientDetail != null) {
            patientDetail.setPatient(patient);
        }
        if (reports != null) {
            reports.forEach(r -> r.setPatient(patient));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(patientRepository.save(patient));
    }

    public ResponseEntity<?> update(Patient updatedPatient, int id) {
        if (updatedPatient == null) {
            return ResponseEntity.badRequest().body("Patient cannot be null");
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient existingPatient = optionalPatient.get();
            List<Report> updatedReportList = updatedPatient.getReports();
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());

            if (updatedReportList != null && !updatedReportList.isEmpty()) {
                existingPatient.setReports(updatedReportList);
            }
            if (updatedPatient.getPatientDetail() != null) {
                existingPatient.setPatientDetail(updatedPatient.getPatientDetail());
            }

            updatedPatient = patientRepository.save(existingPatient);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id: " + id);
        }
    }

    public ResponseEntity<String> delete(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient existingPatient = optionalPatient.get();
            List<Report> reports = existingPatient.getReports();
            if (reports != null) {
                reports.forEach(r -> r.setPatient(null));
            }
            patientRepository.delete(existingPatient);
            return ResponseEntity.ok("Patient with id " + id + " deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id: " + id);
    }

}
