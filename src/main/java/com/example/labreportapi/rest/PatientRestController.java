package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Patient;
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
    public ResponseEntity<List<Patient>> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/asc")
    public ResponseEntity<?> getAllPatientsByOrderFullName() {
        return patientService.findAllByOrderFullNameAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable int id) {
        try {
            return patientService.findById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPatientByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        return patientService.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/search/{identityNumber}")
    public ResponseEntity<?> getPatientByIdentityNumber(@PathVariable long identityNumber) {
        return patientService.findByIdentityNumber(identityNumber);
    }

    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        return patientService.add(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient, @PathVariable int id) {
        return patientService.update(patient, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        return patientService.delete(id);
    }

}
