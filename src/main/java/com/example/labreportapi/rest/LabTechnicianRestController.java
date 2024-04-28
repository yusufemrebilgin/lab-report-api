package com.example.labreportapi.rest;

import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.service.LabTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.labreportapi.util.URIBuilder.*;

@RestController
@RequestMapping("/api/techs")
public class LabTechnicianRestController {

    private final LabTechnicianService labTechnicianService;

    @Autowired
    public LabTechnicianRestController(LabTechnicianService labTechnicianService) {
        this.labTechnicianService = labTechnicianService;
    }

    @GetMapping
    public ResponseEntity<List<LabTechnician>> getAllLabTechnicians() {
        List<LabTechnician> technicians = labTechnicianService.getAll();
        return technicians.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(technicians);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabTechnicianById(@PathVariable int id) {
        return ResponseEntity.ok(labTechnicianService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getLabTechnicianByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(labTechnicianService.getByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("/search/{hospitalId}")
    public ResponseEntity<?> getLabTechnicianByHospitalId(@PathVariable int hospitalId) {
        return ResponseEntity.ok(labTechnicianService.getByHospitalId(hospitalId));
    }

    @PostMapping
    public ResponseEntity<?> addLabTechnician(@RequestBody LabTechnician labTechnician) {
        labTechnician = labTechnicianService.add(labTechnician);
        return ResponseEntity.created(getResourceLocation(labTechnician.getId())).body(labTechnician);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabTechnician(@RequestBody LabTechnician labTechnician, @PathVariable int id) {
        LabTechnician updated =  labTechnicianService.update(labTechnician, id);
        return ResponseEntity.created(getResourceLocation()).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabTechnician(@PathVariable int id) {
        labTechnicianService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
