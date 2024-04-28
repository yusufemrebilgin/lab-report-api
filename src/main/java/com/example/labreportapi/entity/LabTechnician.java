package com.example.labreportapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "lab_technician")
public class LabTechnician extends BaseEntity {

    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @NotEmpty(message = "First name is mandatory")
    private String lastName;

    @Column(name = "hospital_identity_number")
    @Digits(integer = 7, fraction = 0)
    private int hospitalId;

    @OneToMany(mappedBy = "labTechnician", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Report> reports;

}
