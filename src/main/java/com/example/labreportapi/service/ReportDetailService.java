package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportDetailRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportDetailService {

    private final ReportDetailRepository reportDetailRepository;

    @Autowired
    public ReportDetailService(ReportDetailRepository reportDetailRepository) {
        this.reportDetailRepository = reportDetailRepository;
    }

    public ApiResponse<ReportDetail> findById(int id) {
        Optional<ReportDetail> optionalReportDetail = reportDetailRepository.findByReportId(id);
        if (optionalReportDetail.isPresent()) {
            ReportDetail reportDetail = optionalReportDetail.get();
            return new ApiResponse<>(HttpStatus.OK, "Report detail found successfully", reportDetail);
        } else {
            throw new EntityNotFoundException("Report detail not found with report id: " + id);
        }
    }

    public ApiResponse<ReportDetail> add(ReportDetail reportDetail) {
        if (reportDetail == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Report detail cannot be null", null);
        }

        Report report = reportDetail.getReport();
        if (report != null) {
            report.setReportDetail(reportDetail);
        }

        reportDetailRepository.save(reportDetail);
        return new ApiResponse<>(HttpStatus.CREATED, "The report detail successfully created", reportDetail);
    }

    public ApiResponse<ReportDetail> update(ReportDetail updatedReportDetail, int id) {
        if (updatedReportDetail == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Report detail cannot be null", null);
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

            return new ApiResponse<>(HttpStatus.OK, "Report detail updated with id: " + id, updatedReportDetail);
        } else {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Report detail not found with id: " + id, null);
        }
    }

    public ApiResponse<ReportDetail> delete(int id) {
        Optional<ReportDetail> optionalReportDetail = reportDetailRepository.findByReportId(id);
        if (optionalReportDetail.isPresent()) {
            ReportDetail existingReportDetail = optionalReportDetail.get();

            existingReportDetail.getReport().setReportDetail(null);

            reportDetailRepository.delete(existingReportDetail);
            return new ApiResponse<>(HttpStatus.OK, "Report detail with id " + id + " deleted successfully", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND, "Report detail not found with report id: " + id, null);
    }

}
