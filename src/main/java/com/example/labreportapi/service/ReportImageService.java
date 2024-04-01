package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportImageRepository;
import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.entity.ReportImage;
import com.example.labreportapi.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@Slf4j
public class ReportImageService {

    private final ReportImageRepository reportImageRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportImageService(ReportImageRepository reportImageRepository, ReportRepository reportRepository) {
        this.reportImageRepository = reportImageRepository;
        this.reportRepository = reportRepository;
    }

    public ResponseEntity<?> uploadImage(MultipartFile file, int reportId) {
        try {
            ReportImage uploadedImage = ReportImage.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .build();

            Optional<Report> optionalReport = reportRepository.findById(reportId);
            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();
                ReportDetail reportDetail = report.getReportDetail();
                if (reportDetail == null) {
                    reportDetail = new ReportDetail();
                }
                reportDetail.setReportImage(uploadedImage);
                report.setReportDetail(reportDetail);
                reportImageRepository.save(uploadedImage);
                return ResponseEntity.ok(uploadedImage);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no image for report: " + reportId);
            }
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            return ResponseEntity.internalServerError().body("Failed to upload image");
        }
    }

    public byte[] getImageDataByReportId(int reportId) {
        Optional<ReportImage> optionalReportImage = reportImageRepository.findByReportDetailReportId(reportId);
        if (optionalReportImage.isPresent()) {
            try {
                return ImageUtil.decompressImage(optionalReportImage.get().getImageData());
            } catch (IOException | DataFormatException e) {
                log.error("Failed to decompress image", e);
            }
        }
        return null;
    }

    public ResponseEntity<String> deleteImage(int reportId) {
        Optional<ReportImage> optionalReportImage = reportImageRepository.findByReportDetailReportId(reportId);
        if (optionalReportImage.isPresent()) {
            ReportImage existingImage = optionalReportImage.get();
            existingImage.getReportDetail().setReportImage(null);
            reportImageRepository.delete(existingImage);
            return ResponseEntity.ok("Report image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report image not found with id: " + reportId);
        }
    }

}
