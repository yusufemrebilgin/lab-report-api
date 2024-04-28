package com.example.labreportapi.dao;

import com.example.labreportapi.entity.LabTechnician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabTechnicianRepository extends JpaRepository<LabTechnician, Integer> {

    Optional<LabTechnician> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    Optional<LabTechnician> findByHospitalId(int hospitalId);

}
