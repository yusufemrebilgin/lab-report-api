package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Report;
import com.example.labreportapi.service.ReportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return reportService.findAll();
    }

    @GetMapping("/asc")
    public ResponseEntity<List<Report>> getAllReportsByOrderByReportDateAsc() {
        return reportService.findAllByOrderByReportDateAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable int id) {
        try {
            return reportService.findById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addReport(@RequestBody Report report) {
        return reportService.add(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@RequestBody Report report, @PathVariable int id) {
        return reportService.update(report, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable int id) {
        return reportService.delete(id);
    }

}
