package com.example.labreportapi.rest;

import com.example.labreportapi.entity.ReportImage;
import com.example.labreportapi.response.ApiResponse;
import com.example.labreportapi.service.ReportImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reports/{reportId}/details/image")
public class ReportImageRestController {

    private final ReportImageService reportImageService;

    @Autowired
    public ReportImageRestController(ReportImageService reportImageService) {
        this.reportImageService = reportImageService;
    }

    @GetMapping
    public ResponseEntity<?> getReportImage(@PathVariable("reportId") int reportId) {
        byte[] imageData = reportImageService.getImageDataByReportId(reportId);
        if (imageData != null) {
            return ResponseEntity.ok(imageData);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> uploadReportImage(@RequestParam("image")MultipartFile file,
                                               @PathVariable("reportId") int reportId) {

        ApiResponse<ReportImage> apiResponse = reportImageService.uploadImage(file, reportId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReportImage(@PathVariable("reportId") int reportId) {
        ApiResponse<?> apiResponse = reportImageService.deleteImage(reportId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
