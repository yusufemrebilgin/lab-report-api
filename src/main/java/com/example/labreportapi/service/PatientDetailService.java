package com.example.labreportapi.service;

import com.example.labreportapi.dao.PatientDetailRepository;
import com.example.labreportapi.dao.PatientRepository;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
import com.example.labreportapi.response.ApiResponse;
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

    public ResponseEntity<ApiResponse<PatientDetail>> findById(int id) {
        Optional<PatientDetail> optionalPatientDetail = patientDetailRepository.findById(id);
        if (optionalPatientDetail.isPresent()) {
            PatientDetail patientDetail = optionalPatientDetail.get();
            return ApiResponse.build(HttpStatus.OK, "Patient detail found successfully", patientDetail);
        } else {
            throw new EntityNotFoundException("Patient detail not found with id: " + id);
        }
    }
    public ResponseEntity<ApiResponse<PatientDetail>> add(PatientDetail patientDetail, int id) {
        if (patientDetail == null || patientDetail.getIdentityNumber() == 0) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Patient detail or identity number cannot be empty");
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setPatientDetail(patientDetail);
        }

        patientDetailRepository.save(patientDetail);
        return ApiResponse.build(HttpStatus.CREATED, "The patient detail created successfully", patientDetail);
    }

    public ResponseEntity<ApiResponse<PatientDetail>> update(PatientDetail updatedPatientDetail, int id) {
        if (updatedPatientDetail == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Patient detail cannot be null");
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
            return ApiResponse.build(HttpStatus.OK, "Patient detail updated with id: " + id, updatedPatientDetail);
        } else {
            return ApiResponse.build(HttpStatus.NOT_FOUND, "Patient detail not found with id: " + id);
        }
    }
    public ResponseEntity<ApiResponse<Void>> delete(int id) {
        Optional<PatientDetail> optionalPatientDetail = patientDetailRepository.findById(id);
        if (optionalPatientDetail.isPresent()) {
            PatientDetail existingPatientDetail = optionalPatientDetail.get();
            existingPatientDetail.getPatient().setPatientDetail(null);
            patientDetailRepository.delete(existingPatientDetail);
            return ApiResponse.build(HttpStatus.OK, "Patient detail with id " + id + " successfully deleted");
        }
        return ApiResponse.build(HttpStatus.NOT_FOUND, "Patient detail not found with id: " + id);
    }

}
