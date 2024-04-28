package com.example.labreportapi.rest;

import com.example.labreportapi.entity.ReportImage;
import com.example.labreportapi.service.ReportImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.labreportapi.util.URIBuilder.getResourceLocation;

@RestController
@RequestMapping("/api/reports/{id}/details/image")
public class ReportImageRestController {

    private final ReportImageService reportImageService;

    @Autowired
    public ReportImageRestController(ReportImageService reportImageService) {
        this.reportImageService = reportImageService;
    }

    @GetMapping
    public ResponseEntity<?> getReportImage(@PathVariable int id) {
        byte[] imageData = reportImageService.getImageDataByReportId(id);
        return imageData != null ? ResponseEntity.ok(imageData) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> uploadReportImage(@RequestParam("image")MultipartFile file, @PathVariable int id) {
        ReportImage reportImage = reportImageService.uploadImage(file, id);
        return ResponseEntity.created(getResourceLocation()).body(reportImage);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReportImage(@PathVariable int id) {
        reportImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

}
