package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^RP\\d{4}-\\d{4}$",
             message = "Report code must start with RP and continue with 8 digits (For example, RP0000-0000")
    private String reportCode;

    private String diagnosisTitle;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "lab_technician_id")
    private LabTechnician labTechnician;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_detail_id")
    private ReportDetail reportDetail;

}
