package com.example.labreportapi.service;

import com.example.labreportapi.dao.PatientDetailRepository;
import com.example.labreportapi.dao.PatientRepository;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
import com.example.labreportapi.exception.PatientDetailNotFoundException;
import com.example.labreportapi.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDetailRepository patientDetailRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientDetailRepository patientDetailRepository) {
        this.patientRepository = patientRepository;
        this.patientDetailRepository = patientDetailRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    public List<Patient> getAllPatientsByOrderFullNameAsc() {
        return patientRepository.findAllByOrderByFirstNameAscLastNameAsc();
    }

    public List<Patient> getAllPatientsByFirstNameStartsWith(String prefix) {
        return patientRepository.findByFirstNameIgnoreCaseStartingWith(prefix);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient newPatient, int id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setFirstName(newPatient.getFirstName());
                    patient.setLastName(newPatient.getLastName());
                    if (newPatient.getPatientDetail() != null)
                        patient.setPatientDetail(newPatient.getPatientDetail());
                    if (newPatient.getReports() != null)
                        patient.setReports(newPatient.getReports());
                    return patientRepository.save(patient);
                }).orElseGet(() -> {
                    newPatient.setId(id);
                    return patientRepository.save(newPatient);
                });
    }

    public void deletePatient(int id) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        patientRepository.delete(existingPatient);
    }

    public PatientDetail getPatientDetailByPatientId(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        PatientDetail patientDetail = patient.getPatientDetail();
        if (patientDetail == null) {
            throw new PatientDetailNotFoundException(id);
        }
        return patientDetail;
    }

    public PatientDetail addPatientDetail(PatientDetail patientDetail, int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        patient.setPatientDetail(patientDetail);
        return patientDetailRepository.save(patientDetail);
    }

    public PatientDetail updatePatientDetail(PatientDetail newPatientDetail, int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        PatientDetail savedDetail = patient.getPatientDetail();
        if (savedDetail != null) {
            if (newPatientDetail.getIdentityNumber() != 0L)
                savedDetail.setIdentityNumber(newPatientDetail.getIdentityNumber());
            if (newPatientDetail.getEmail() != null)
                savedDetail.setEmail(newPatientDetail.getEmail());
            if (newPatientDetail.getPhoneNumber() != null)
                savedDetail.setPhoneNumber(newPatientDetail.getPhoneNumber());
            return patientDetailRepository.save(savedDetail);
        } else {
            patient.setPatientDetail(newPatientDetail);
            return patientDetailRepository.save(newPatientDetail);
        }
    }

    public void deletePatientDetail(int id) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        PatientDetail existingDetail = existingPatient.getPatientDetail();
        if (existingDetail == null) {
            throw new PatientDetailNotFoundException(id);
        }
        existingPatient.setPatientDetail(null);
        patientDetailRepository.delete(existingDetail);
    }

}
