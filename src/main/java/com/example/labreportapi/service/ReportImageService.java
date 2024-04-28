package com.example.labreportapi.service;

import com.example.labreportapi.dao.ReportImageRepository;
import com.example.labreportapi.dao.ReportRepository;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.entity.ReportDetail;
import com.example.labreportapi.entity.ReportImage;
import com.example.labreportapi.exception.ReportImageNotFoundException;
import com.example.labreportapi.exception.ReportNotFoundException;
import com.example.labreportapi.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Slf4j
@Service
public class ReportImageService {

    private final ReportImageRepository reportImageRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportImageService(ReportImageRepository reportImageRepository, ReportRepository reportRepository) {
        this.reportImageRepository = reportImageRepository;
        this.reportRepository = reportRepository;
    }

    public ReportImage uploadImage(MultipartFile file, int reportId) {
        try {
            ReportImage uploadedImage = ReportImage.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .build();

            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException(reportId));

            ReportDetail reportDetail = report.getReportDetail();
            if (reportDetail == null) {
                reportDetail = new ReportDetail();
            }

            report.setReportDetail(reportDetail);
            reportDetail.setReportImage(uploadedImage);
            return reportImageRepository.save(uploadedImage);
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image");
        }
    }

    public byte[] getImageDataByReportId(int reportId) {
       ReportImage reportImage = reportImageRepository.findByReportDetailReportId(reportId)
                .orElseThrow(() -> new ReportImageNotFoundException(reportId));
        try {
            return ImageUtil.decompressImage(reportImage.getImageData());
        } catch (IOException | DataFormatException e) {
            log.error("Failed to decompress image", e);
        }
        return null;
    }

    public void deleteImage(int reportId) {
        Optional<ReportImage> optionalReportImage = reportImageRepository.findByReportDetailReportId(reportId);
        ReportImage existingReportImage = reportImageRepository.findByReportDetailReportId(reportId)
                .orElseThrow(() -> new ReportImageNotFoundException(reportId));
        ReportDetail reportDetail = existingReportImage.getReportDetail();
        reportDetail.setReportImage(null);
        reportImageRepository.delete(existingReportImage);
    }

}
