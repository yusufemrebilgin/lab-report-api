package com.example.labreportapi.dao;

import com.example.labreportapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByFirstNameIgnoreCaseStartingWith(String prefix);
    List<Patient> findAllByOrderByFirstNameAscLastNameAsc();

}
