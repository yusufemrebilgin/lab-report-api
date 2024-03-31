package com.example.labreportapi.dao;

import com.example.labreportapi.entity.ReportImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportImageRepository extends JpaRepository<ReportImage, Integer> {

    Optional<ReportImage> findByReportDetailReportId(int reportId);

}
