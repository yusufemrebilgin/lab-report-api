package com.example.labreportapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "report")
public class Report extends BaseEntity {

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

}
