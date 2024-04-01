package com.example.labreportapi.service;

import com.example.labreportapi.dao.PatientDetailRepository;
import com.example.labreportapi.dao.PatientRepository;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientDetailService {

    private final PatientDetailRepository patientDetailRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientDetailService(PatientDetailRepository patientDetailRepository, PatientRepository patientRepository) {
        this.patientDetailRepository = patientDetailRepository;
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<PatientDetail> findById(int id) {
        Optional<PatientDetail> optionalPatientDetail = patientDetailRepository.findById(id);
        if (optionalPatientDetail.isPresent()) {
            PatientDetail patientDetail = optionalPatientDetail.get();
            return ResponseEntity.ok(patientDetail);
        } else {
            throw new EntityNotFoundException("Patient detail not found with id: " + id);
        }
    }
    public ResponseEntity<?> add(PatientDetail patientDetail, int id) {
        if (patientDetail == null || patientDetail.getIdentityNumber() == 0) {
            return ResponseEntity.badRequest().body("Patient detail or identity number cannot be empty");
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setPatientDetail(patientDetail);
        }

        patientDetailRepository.save(patientDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDetail);
    }

    public ResponseEntity<?> update(PatientDetail updatedPatientDetail, int id) {
        if (updatedPatientDetail == null) {
            return ResponseEntity.badRequest().body("Patient detail cannot be null");
        }

        Optional<PatientDetail> optionalPatientDetail = patientDetailRepository.findById(id);
        if (optionalPatientDetail.isPresent()) {
            PatientDetail existingPatientDetail = optionalPatientDetail.get();
            existingPatientDetail.setEmail(updatedPatientDetail.getEmail());
            existingPatientDetail.setPhoneNumber(updatedPatientDetail.getPhoneNumber());
            if (updatedPatientDetail.getPatient() != null) {
                existingPatientDetail.setPatient(updatedPatientDetail.getPatient());
            }

            updatedPatientDetail = patientDetailRepository.save(existingPatientDetail);
            return ResponseEntity.ok(updatedPatientDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient detail not found with id: " + id);
        }
    }
    public ResponseEntity<String> delete(int id) {
        Optional<PatientDetail> optionalPatientDetail = patientDetailRepository.findById(id);
        if (optionalPatientDetail.isPresent()) {
            PatientDetail existingPatientDetail = optionalPatientDetail.get();
            existingPatientDetail.getPatient().setPatientDetail(null);
            patientDetailRepository.delete(existingPatientDetail);
            return ResponseEntity.ok("Patient detail with id " + id + " successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient detail not found with id: " + id);
    }

}
