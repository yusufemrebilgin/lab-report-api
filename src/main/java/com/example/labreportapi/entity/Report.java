package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "report_code")
    private String reportCode;

    @Column(name = "diagnosis_title")
    private String diagnosisTitle;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "lab_technician_id")
    private LabTechnician labTechnician;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_detail_id")
    private ReportDetail reportDetail;

    public Report() {}

    public Report(String reportCode, String diagnosisTitle) {
        this.reportCode = reportCode;
        this.diagnosisTitle = diagnosisTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getDiagnosisTitle() {
        return diagnosisTitle;
    }

    public void setDiagnosisTitle(String diagnosisTitle) {
        this.diagnosisTitle = diagnosisTitle;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LabTechnician getLabTechnician() {
        return labTechnician;
    }

    public void setLabTechnician(LabTechnician labTechnician) {
        this.labTechnician = labTechnician;
    }

    public ReportDetail getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(ReportDetail reportDetail) {
        this.reportDetail = reportDetail;
        if (reportDetail != null) {
            reportDetail.setReport(this);
        }
    }
}
