package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ApiResponse<List<Report>> findAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NO_CONTENT, null, reports);
        }
        return new ApiResponse<>(HttpStatus.OK, "The report(s) found successfully", reports);
    }

    public ApiResponse<List<Report>> findAllByOrderByReportDateAsc() {
        List<Report> reports = reportRepository.findAllByOrderByReportDetailReportDateAsc();
        if (reports.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NO_CONTENT, null, reports);
        }
        return new ApiResponse<>(HttpStatus.OK, "The report(s) successfully sorted by dates", reports);
    }

    public ApiResponse<Report> findById(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return new ApiResponse<>(HttpStatus.OK, "The report found successfully", report);
        } else {
            throw new EntityNotFoundException("Report not found with id: " + id);
        }
    }

    @Transactional
    public ApiResponse<Report> add(Report report) {
        if (report == null || report.getReportCode() == null || report.getDiagnosisTitle() == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Provided fields cannot be null", report);
        }

        if (reportRepository.existsByReportCode(report.getReportCode())) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Report code already exists", report);
        }

        ReportDetail reportDetail = report.getReportDetail();
        if (reportDetail != null) {
            reportDetail.setReport(report);
        }

        reportRepository.save(report);
        return new ApiResponse<>(HttpStatus.CREATED, "The report successfully created", report);
    }

    @Transactional
    public ApiResponse<Report> update(Report updatedReport, int id) {
        if (updatedReport == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Report cannot be null", null);
        }

        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report existingReport = optionalReport.get();

            if (updatedReport.getReportCode() != null
            && !reportRepository.existsByReportCode(updatedReport.getReportCode())) {
                existingReport.setReportCode(updatedReport.getReportCode());
            }

            existingReport.setDiagnosisTitle(updatedReport.getDiagnosisTitle());
            existingReport.setPatient(updatedReport.getPatient());
            existingReport.setLabTechnician(updatedReport.getLabTechnician());

            if (updatedReport.getReportDetail() != null) {
                existingReport.setReportDetail(updatedReport.getReportDetail());
            }
            updatedReport = reportRepository.save(existingReport);

            return new ApiResponse<>(HttpStatus.OK, "Report updated with id: " + id, updatedReport);

        } else {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Report not found with id: " + id, null);
        }

    }

    @Transactional
    public ApiResponse<Report> delete(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report existingReport = optionalReport.get();
            reportRepository.delete(existingReport);
            return new ApiResponse<>(HttpStatus.OK, "Report with id " + id + " deleted successfully", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND, "Report not found with id: " + id, null);
    }

}
