package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "report_image")
public class ReportImage extends BaseEntity {
    
    private String name;

    private String type;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imageData;

    @JsonIgnore
    @OneToOne(mappedBy = "reportImage",
              cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    private ReportDetail reportDetail;

}
