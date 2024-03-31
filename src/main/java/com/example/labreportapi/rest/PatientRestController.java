package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.response.ApiResponse;
import com.example.labreportapi.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

    private final PatientService patientService;

    @Autowired
    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Patient>>> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patient>> getPatientById(@PathVariable int id) {
        try {
            return patientService.findById(id);
        } catch (EntityNotFoundException e) {
            return ApiResponse.build(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Patient>> addPatient(@RequestBody Patient patient) {
        return patientService.add(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Patient>> updatePatient(@RequestBody Patient patient, @PathVariable int id) {
        return patientService.update(patient, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable int id) {
        return patientService.delete(id);
    }

}
