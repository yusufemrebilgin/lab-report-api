package com.example.labreportapi.dao;

import com.example.labreportapi.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findAllByOrderByReportDetailReportDateAsc();
    List<Report> findAllByOrderByReportDetailReportDateDesc();

}
