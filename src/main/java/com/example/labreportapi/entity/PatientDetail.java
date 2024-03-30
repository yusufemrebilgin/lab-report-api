package com.example.labreportapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_detail")
public class PatientDetail extends BaseEntity {

    @Column(name = "tr_identity_number")
    private long identityNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "patientDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Patient patient;

    public PatientDetail() {}

    public PatientDetail(long identityNumber, String email, String phoneNumber) {
        this.identityNumber = identityNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(long identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
