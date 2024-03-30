package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "report_detail")
public class ReportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "diagnosis_details")
    private String diagnosisDetails;

    @Column(name = "report_date")
    private Date reportDate;

    @Lob
    @Column(name = "report_photo", columnDefinition = "BLOB")
    private byte[] reportPhoto;

    @OneToOne(mappedBy = "reportDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Report report;

    public ReportDetail() {}

    public ReportDetail(String diagnosisDetails, Date reportDate, byte[] reportPhoto) {
        this.diagnosisDetails = diagnosisDetails;
        this.reportDate = reportDate;
        this.reportPhoto = reportPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosisDetails() {
        return diagnosisDetails;
    }

    public void setDiagnosisDetails(String diagnosisDetails) {
        this.diagnosisDetails = diagnosisDetails;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public byte[] getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(byte[] reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
