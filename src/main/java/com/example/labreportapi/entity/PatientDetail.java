package com.example.labreportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "patient_detail")
public class PatientDetail extends BaseEntity {

    @Min(value = 10000000000L)
    @Max(value = 99999999999L)
    @Column(name = "tr_identity_number")
    private long identityNumber;

    @Email
    private String email;

    @Pattern(regexp = "^\\+90[0-9]{10}$")
    private String phoneNumber;

    @OneToOne(mappedBy = "patientDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Patient patient;

}
