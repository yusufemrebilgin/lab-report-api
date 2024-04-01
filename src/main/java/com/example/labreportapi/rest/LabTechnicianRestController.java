package com.example.labreportapi.rest;

import com.example.labreportapi.entity.LabTechnician;
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
    public ResponseEntity<List<LabTechnician>> getAllLabTechnicians() {
        return labTechnicianService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabTechnicianById(@PathVariable int id) {
        try {
            return labTechnicianService.findById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getLabTechnicianByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        return labTechnicianService.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/search/{hospitalId}")
    public ResponseEntity<?> getLabTechnicianByHospitalId(@PathVariable int hospitalId) {
        return labTechnicianService.findByHospitalId(hospitalId);
    }

    @PostMapping
    public ResponseEntity<?> addLabTechnician(@RequestBody LabTechnician labTechnician) {
        return labTechnicianService.add(labTechnician);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabTechnician(@RequestBody LabTechnician labTechnician, @PathVariable int id) {
        return labTechnicianService.update(labTechnician, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabTechnician(@PathVariable int id) {
        return labTechnicianService.delete(id);
    }

}
