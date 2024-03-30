package com.example.labreportapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_detail_id")
    private PatientDetail patientDetail;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Report> reports;

    public Patient() {}

    public Patient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PatientDetail getPatientDetail() {
        return patientDetail;
    }

    public void setPatientDetail(PatientDetail patientDetail) {
        this.patientDetail = patientDetail;
    }
}
