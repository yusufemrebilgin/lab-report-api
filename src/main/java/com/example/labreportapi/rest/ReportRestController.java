package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Report;
import com.example.labreportapi.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Report>>> getAllReports() {
        ApiResponse<List<Report>> apiResponse = reportService.findAll();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/asc")
    public ResponseEntity<ApiResponse<List<Report>>> getAllReportsByOrderByReportDateAsc() {
        ApiResponse<List<Report>> apiResponse = reportService.findAllByOrderByReportDateAsc();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Report>> getReportById(@PathVariable int id) {
        try {
            ApiResponse<Report> apiResponse = reportService.findById(id);
            return ResponseEntity.ok(apiResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Report>> addReport(@RequestBody Report report) {
        ApiResponse<Report> apiResponse = reportService.add(report);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Report>> updateReport(@RequestBody Report report, @PathVariable int id) {
        ApiResponse<Report> apiResponse = reportService.update(report, id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Report>> deleteReportById(@PathVariable int id) {
        ApiResponse<Report> apiResponse = reportService.delete(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
