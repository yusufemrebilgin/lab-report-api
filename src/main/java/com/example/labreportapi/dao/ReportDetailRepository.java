package com.example.labreportapi.dao;

import com.example.labreportapi.entity.ReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportDetailRepository extends JpaRepository<ReportDetail, Integer> {

    Optional<ReportDetail> findByReportId(int id);

}
