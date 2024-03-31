package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ApiResponse<List<Report>>> findAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ApiResponse.build(HttpStatus.OK, "The report(s) found successfully", reports);
    }

    public ResponseEntity<ApiResponse<List<Report>>> findAllByOrderByReportDateAsc() {
        List<Report> reports = reportRepository.findAllByOrderByReportDetailReportDateAsc();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ApiResponse.build(HttpStatus.OK, "The report(s) successfully sorted by dates", reports);
    }

    public ResponseEntity<ApiResponse<Report>> findById(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return ApiResponse.build(HttpStatus.OK, "The report found successfully", report);
        } else {
            throw new EntityNotFoundException("Report not found with id: " + id);
        }
    }

    public ResponseEntity<ApiResponse<Report>> add(Report report) {
        if (report == null || report.getReportCode() == null || report.getDiagnosisTitle() == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Provided fields cannot be null");
        }

        if (reportRepository.existsByReportCode(report.getReportCode())) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Report code already exists");
        }

        ReportDetail reportDetail = report.getReportDetail();
        if (reportDetail != null) {
            reportDetail.setReport(report);
        }

        reportRepository.save(report);
        return ApiResponse.build(HttpStatus.CREATED, "The report successfully created", report);
    }

    public ResponseEntity<ApiResponse<Report>> update(Report updatedReport, int id) {
        if (updatedReport == null) {
            return ApiResponse.build(HttpStatus.BAD_REQUEST, "Report cannot be null");
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
            return ApiResponse.build(HttpStatus.OK, "Report updated with id: " + id, updatedReport);

        } else {
            return ApiResponse.build(HttpStatus.NOT_FOUND, "Report not found with id: " + id);
        }

    }

    public ResponseEntity<ApiResponse<Void>> delete(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report existingReport = optionalReport.get();

            Patient patient = existingReport.getPatient();
            if (patient != null) {
                patient.getReports().remove(existingReport);
            }

            LabTechnician labTechnician = existingReport.getLabTechnician();
            if (labTechnician != null) {
                labTechnician.getReports().remove(existingReport);
            }
            reportRepository.delete(existingReport);

            return ApiResponse.build(HttpStatus.OK, "Report with id " + id + " deleted successfully");
        }
        return ApiResponse.build(HttpStatus.NOT_FOUND, "Report not found with id: " + id);
    }

}
