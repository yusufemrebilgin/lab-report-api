package com.example.labreportapi.dao;

import com.example.labreportapi.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    boolean existsByReportCode(String reportCode);

}
