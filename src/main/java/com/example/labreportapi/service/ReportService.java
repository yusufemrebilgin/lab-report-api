package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportDetailRepository;
import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.exception.ReportDetailNotFoundException;
import com.example.labreportapi.exception.ReportNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportDetailRepository reportDetailRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, ReportDetailRepository reportDetailRepository) {
        this.reportRepository = reportRepository;
        this.reportDetailRepository = reportDetailRepository;
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report getReportById(int id) {
        return reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(id));
    }

    public List<Report> getAllReportsByReportDateOrder(boolean ascending) {
        return ascending ? reportRepository.findAllByOrderByReportDetailReportDateAsc()
                         : reportRepository.findAllByOrderByReportDetailReportDateDesc();
    }

    public Report addReport(Report report) {
        return reportRepository.save(report);
    }

    public Report updateReport(Report newReport, int id) {
        return reportRepository.findById(id)
                .map(report -> {
                    if (newReport.getReportCode() != null && !newReport.getReportCode().equals(report.getReportCode()))
                        report.setReportCode(newReport.getReportCode());
                    if (newReport.getDiagnosisTitle() != null)
                        report.setDiagnosisTitle(newReport.getDiagnosisTitle());
                    if (newReport.getPatient() != null)
                        report.setPatient(newReport.getPatient());
                    if (newReport.getLabTechnician() != null)
                        report.setLabTechnician(newReport.getLabTechnician());
                    if (newReport.getReportDetail() != null)
                        report.setReportDetail(newReport.getReportDetail());
                    return reportRepository.save(report);
                }).orElseThrow(() -> new ReportNotFoundException(id));
    }

    public void deleteReport(int id) {
        Report existingReport = getReportById(id);
        existingReport.setPatient(null);
        existingReport.setLabTechnician(null);
        reportRepository.delete(existingReport);
    }

    public ReportDetail getReportDetailById(int id) {
        Report existingReport = getReportById(id);
        ReportDetail existingDetail = existingReport.getReportDetail();
        if (existingDetail == null) {
            throw new ReportDetailNotFoundException(id);
        }
        return existingDetail;
    }

    public ReportDetail addReportDetail(ReportDetail reportDetail, int id) {
        Report report = getReportById(id);
        report.setReportDetail(reportDetail);
        return reportDetailRepository.save(reportDetail);
    }

    public ReportDetail updateReportDetail(ReportDetail newReportDetail, int id) {
        Report report = getReportById(id);
        ReportDetail savedDetail = report.getReportDetail();
        if (savedDetail != null) {
            if (newReportDetail.getDiagnosisDetails() != null)
                savedDetail.setDiagnosisDetails(newReportDetail.getDiagnosisDetails());
            if (newReportDetail.getReportDate() != null)
                savedDetail.setReportDate(newReportDetail.getReportDate());
            return reportDetailRepository.save(savedDetail);
        } else {
            report.setReportDetail(newReportDetail);
            return reportDetailRepository.save(newReportDetail);
        }
    }

    public void deleteReportDetail(int id) {
        Report existingReport = getReportById(id);
        ReportDetail existingDetail = existingReport.getReportDetail();
        if (existingDetail == null) {
            throw new ReportDetailNotFoundException(id);
        }
        existingReport.setReportDetail(null);
        reportDetailRepository.delete(existingDetail);
    }

}
