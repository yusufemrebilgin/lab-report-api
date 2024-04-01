package com.example.labreportapi.dao;

import com.example.labreportapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    Patient findByPatientDetailIdentityNumber(long identityNumber);
    List<Patient> findAllByOrderByFirstNameAscLastNameAsc();

}
