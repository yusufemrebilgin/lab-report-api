package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.labreportapi.util.URIBuilder.*;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports =  reportService.getAll();
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable int id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/asc")
    public ResponseEntity<List<Report>> getAllReportsByOrderByReportDateAsc() {
        List<Report> sortedReports = reportService.getAllReportsByReportDateOrder(true);
        return sortedReports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sortedReports);
    }

    @GetMapping("/desc")
    public ResponseEntity<List<Report>> getAllReportsByOrderByReportDateDesc() {
        List<Report> sortedReports = reportService.getAllReportsByReportDateOrder(false);
        return sortedReports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sortedReports);
    }

    @PostMapping
    public ResponseEntity<?> addReport(@Valid @RequestBody Report report) {
        report = reportService.addReport(report);
        return ResponseEntity.created(getResourceLocation(report.getId())).body(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@Valid @RequestBody Report report, @PathVariable int id) {
        report = reportService.updateReport(report, id);
        return ResponseEntity.created(getResourceLocation()).body(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable int id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getReportDetail(@PathVariable int id) {
        return ResponseEntity.ok(reportService.getReportDetailById(id));
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<?> addReportDetail(@Valid @RequestBody ReportDetail reportDetail, @PathVariable int id) {
        reportDetail = reportService.addReportDetail(reportDetail, id);
        return ResponseEntity.created(getResourceLocation()).body(reportDetail);
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateReportDetail(@Valid @RequestBody ReportDetail reportDetail, @PathVariable int id) {
        ReportDetail updatedDetail = reportService.updateReportDetail(reportDetail, id);
        return ResponseEntity.created(getResourceLocation()).body(updatedDetail);
    }

    @DeleteMapping("/{id}/details")
    public ResponseEntity<?> deleteReportDetail(@PathVariable int id) {
        reportService.deleteReportDetail(id);
        return ResponseEntity.noContent().build();
    }

}
