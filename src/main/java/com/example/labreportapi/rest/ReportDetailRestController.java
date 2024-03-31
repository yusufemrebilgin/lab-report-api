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
            return reportDetailService.findById(id);
        } catch (EntityNotFoundException e) {
            return ApiResponse.build(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportDetail>> addReportDetail(@RequestBody ReportDetail detail, @PathVariable("reportId") int id) {
        return reportDetailService.add(detail, id);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ReportDetail>> updateReportDetail(@RequestBody ReportDetail detail, @PathVariable("reportId") int id) {
        return reportDetailService.update(detail, id);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<ReportDetail>> deleteReportDetail(@PathVariable("reportId") int id) {
        return reportDetailService.delete(id);
    }

}
