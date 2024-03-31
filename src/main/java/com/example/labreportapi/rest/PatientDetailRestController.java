package com.example.labreportapi.rest;

import com.example.labreportapi.entity.PatientDetail;
import com.example.labreportapi.response.ApiResponse;
import com.example.labreportapi.service.PatientDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients/{patientId}/details")
public class PatientDetailRestController {

    private final PatientDetailService patientDetailService;

    @Autowired
    public PatientDetailRestController(PatientDetailService patientDetailService) {
        this.patientDetailService = patientDetailService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PatientDetail>> getPatientDetailById(@PathVariable("patientId") int id) {
        try {
            return patientDetailService.findById(id);
        } catch (EntityNotFoundException e) {
            return ApiResponse.build(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PatientDetail>> addPatientDetail(@RequestBody PatientDetail detail, @PathVariable("patientId") int id) {
        return patientDetailService.add(detail, id);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PatientDetail>> updatePatientDetail(@RequestBody PatientDetail detail, @PathVariable("patientId") int id) {
        return patientDetailService.update(detail, id);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deletePatientDetail(@PathVariable("patientId") int id) {
        return patientDetailService.delete(id);
    }

}
