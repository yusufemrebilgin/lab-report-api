package com.example.labreportapi.dao;

import com.example.labreportapi.entity.PatientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDetailRepository extends JpaRepository<PatientDetail, Integer> {

    boolean existsByIdentityNumber(long identityNumber);

}
