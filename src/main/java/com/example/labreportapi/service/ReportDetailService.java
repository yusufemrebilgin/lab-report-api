package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportDetailRepository;
import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportDetailService {

    private final ReportDetailRepository reportDetailRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportDetailService(ReportDetailRepository reportDetailRepository, ReportRepository reportRepository) {
        this.reportDetailRepository = reportDetailRepository;
        this.reportRepository = reportRepository;
    }

    public ResponseEntity<ApiResponse<ReportDetail>> findById(int id) {
        Optional<ReportDetail> optionalReportDetail = reportDetailRepository.findByReportId(id);
        if (optionalReportDetail.isPresent()) {
            ReportDetail reportDetail = optionalReportDetail.get();
            return ApiResponse.build(HttpStatus.OK, "Report detail found successfully", reportDetail);
        } else {
            throw new EntityNotFoundException("Report detail not found with report id: " + id);
        }
    }

    public ResponseEntity<ApiResponse<ReportDetail>> add(ReportDetail reportDetail, int id) {
        if (reportDetail == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Report detail cannot be null");
        }

        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setReportDetail(reportDetail);
        }

        reportDetailRepository.save(reportDetail);
        return ApiResponse.build(HttpStatus.CREATED, "The report detail successfully created", reportDetail);
    }

    public ResponseEntity<ApiResponse<ReportDetail>> update(ReportDetail updatedReportDetail, int id) {
        if (updatedReportDetail == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Report detail cannot be null");
        }

        Optional<ReportDetail> optionalReportDetail = reportDetailRepository.findByReportId(id);
        if (optionalReportDetail.isPresent()) {
            ReportDetail existingReportDetail = optionalReportDetail.get();
            existingReportDetail.setDiagnosisDetails(updatedReportDetail.getDiagnosisDetails());
            existingReportDetail.setReportDate(updatedReportDetail.getReportDate());
            existingReportDetail.setReportImage(updatedReportDetail.getReportImage());

            if (updatedReportDetail.getReport() != null) {
                existingReportDetail.setReport(updatedReportDetail.getReport());
            }

            updatedReportDetail = reportDetailRepository.save(existingReportDetail);
            return ApiResponse.build(HttpStatus.OK, "Report detail updated with id: " + id, updatedReportDetail);
        } else {
            return ApiResponse.build(HttpStatus.NOT_FOUND, "Report detail not found with id: " + id);
        }
    }

    public ResponseEntity<ApiResponse<ReportDetail>> delete(int id) {
        Optional<ReportDetail> optionalReportDetail = reportDetailRepository.findByReportId(id);
        if (optionalReportDetail.isPresent()) {
            ReportDetail existingReportDetail = optionalReportDetail.get();
            existingReportDetail.getReport().setReportDetail(null);
            reportDetailRepository.delete(existingReportDetail);
            return ApiResponse.build(HttpStatus.OK, "Report detail with id " + id + " deleted successfully");
        }
        return ApiResponse.build(HttpStatus.NOT_FOUND, "Report detail not found with report id: " + id);
    }

}
