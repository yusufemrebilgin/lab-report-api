package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "report_detail")
public class ReportDetail extends BaseEntity {

    @Column(name = "diagnosis_details")
    private String diagnosisDetails;

    @Column(name = "report_date")
    private Date reportDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_image_id")
    private ReportImage reportImage;

    @JsonIgnore
    @OneToOne(mappedBy = "reportDetail",
              cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    private Report report;

}
