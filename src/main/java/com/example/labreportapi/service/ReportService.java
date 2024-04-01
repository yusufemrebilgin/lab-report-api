package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
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

    public ResponseEntity<List<Report>> findAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    public ResponseEntity<List<Report>> findAllByOrderByReportDateAsc() {
        List<Report> reports = reportRepository.findAllByOrderByReportDetailReportDateAsc();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    public ResponseEntity<Report> findById(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return ResponseEntity.ok(report);
        } else {
            throw new EntityNotFoundException("Report not found with id: " + id);
        }
    }

    public ResponseEntity<?> add(Report report) {
        if (report == null || report.getReportCode() == null || report.getDiagnosisTitle() == null) {
            return ResponseEntity.badRequest().body("Provided fields cannot be null");
        }

        if (reportRepository.existsByReportCode(report.getReportCode())) {
            return ResponseEntity.badRequest().body("Report code already exists");
        }

        ReportDetail reportDetail = report.getReportDetail();
        if (reportDetail != null) {
            reportDetail.setReport(report);
        }

        reportRepository.save(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    public ResponseEntity<?> update(Report updatedReport, int id) {
        if (updatedReport == null) {
            return ResponseEntity.badRequest().body("Report cannot be null");
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
            return ResponseEntity.ok(updatedReport);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with id: " + id);
        }
    }

    public ResponseEntity<String> delete(int id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Patient patient = report.getPatient();
            LabTechnician labTechnician = report.getLabTechnician();

            if (patient != null) {
                patient.getReports().remove(report);
            }
            if (labTechnician != null) {
                labTechnician.getReports().remove(report);
            }

            reportRepository.delete(report);
            return ResponseEntity.ok("Report with id " + id + " deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with id: " + id);
    }

}
