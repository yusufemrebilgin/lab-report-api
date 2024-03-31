package com.example.labreportapi.rest;

import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.response.ApiResponse;
import com.example.labreportapi.service.LabTechnicianService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
public class LabTechnicianRestController {

    private final LabTechnicianService labTechnicianService;

    @Autowired
    public LabTechnicianRestController(LabTechnicianService labTechnicianService) {
        this.labTechnicianService = labTechnicianService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LabTechnician>>> getAllLabTechnicians() {
        return labTechnicianService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LabTechnician>> getLabTechnicianById(@PathVariable int id) {
        try {
            return labTechnicianService.findById(id);
        } catch (EntityNotFoundException e) {
            return ApiResponse.build(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LabTechnician>> addLabTechnician(@RequestBody LabTechnician labTechnician) {
        return labTechnicianService.add(labTechnician);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LabTechnician>> updateLabTechnician(@RequestBody LabTechnician labTechnician, @PathVariable int id) {
        return labTechnicianService.update(labTechnician, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLabTechnician(@PathVariable int id) {
        return labTechnicianService.delete(id);
    }

}
