package com.example.labreportapi.rest;

import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.response.ApiResponse;
import com.example.labreportapi.service.ReportDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reports/{reportId}/details")
public class ReportDetailRestController {

    private final ReportDetailService reportDetailService;

    @Autowired
    public ReportDetailRestController(ReportDetailService reportDetailService) {
        this.reportDetailService = reportDetailService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ReportDetail>> getReportDetailById(@PathVariable("reportId") int id) {
        try {
            ApiResponse<ReportDetail> apiResponse = reportDetailService.findById(id);
            return ResponseEntity.ok(apiResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportDetail>> addReportDetail(@PathVariable("reportId") int id, @RequestBody ReportDetail detail) {
        ApiResponse<ReportDetail> apiResponse = reportDetailService.add(detail);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ReportDetail>> updateReportDetail(@PathVariable("reportId") int id, @RequestBody ReportDetail detail) {
        ApiResponse<ReportDetail> apiResponse = reportDetailService.update(detail, id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<ReportDetail>> deleteReportDetail(@PathVariable("reportId") int id) {
        ApiResponse<ReportDetail> apiResponse = reportDetailService.delete(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
