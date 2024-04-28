package com.example.labreportapi.rest;

import com.example.labreportapi.entity.Patient;
import com.example.labreportapi.entity.PatientDetail;
import com.example.labreportapi.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.labreportapi.util.URIBuilder.*;

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
        List<Patient> patients = patientService.getAllPatients();
        return patients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable int id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/asc")
    public ResponseEntity<?> getAllPatientsByOrderFullName() {
        List<Patient> sortedPatients = patientService.getAllPatientsByOrderFullNameAsc();
        return sortedPatients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sortedPatients);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPatientsByFirstNameStartingWith(@RequestParam String name) {
        List<Patient> resultList =  patientService.getAllPatientsByFirstNameStartsWith(name);
        return resultList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultList);
    }

    @PostMapping
    public ResponseEntity<?> addPatient(@Valid @RequestBody Patient patient) {
        patient = patientService.addPatient(patient);
        return ResponseEntity.created(getResourceLocation(patient.getId())).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@Valid @RequestBody Patient patient, @PathVariable int id) {
        Patient updatedPatient = patientService.updatePatient(patient, id);
        return ResponseEntity.created(getResourceLocation()).body(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/details")
    public ResponseEntity<?> getPatientDetail(@PathVariable int id) {
        return ResponseEntity.ok(patientService.getPatientDetailByPatientId(id));
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<?> addPatientDetail(@Valid @RequestBody PatientDetail patientDetail, @PathVariable int id) {
        patientDetail = patientService.addPatientDetail(patientDetail, id);
        return ResponseEntity.created(getResourceLocation()).body(patientDetail);
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updatePatientDetail(@Valid @RequestBody PatientDetail patientDetail,
                                                 @PathVariable int id) {
        PatientDetail updatedDetail =  patientService.updatePatientDetail(patientDetail, id);
        return ResponseEntity.created(getResourceLocation()).body(updatedDetail);
    }

    @DeleteMapping("/{id}/details")
    public ResponseEntity<?> deletePatientDetail(@PathVariable int id) {
        patientService.deletePatientDetail(id);
        return ResponseEntity.noContent().build();
    }

}
