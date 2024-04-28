package com.example.labreportapi.service;

import com.example.labreportapi.dao.LabTechnicianRepository;
import com.example.labreportapi.entity.LabTechnician;
import com.example.labreportapi.entity.Report;
import com.example.labreportapi.exception.LabTechnicianNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabTechnicianService {

    private final LabTechnicianRepository labTechnicianRepository;

    @Autowired
    public LabTechnicianService(LabTechnicianRepository labTechnicianRepository) {
        this.labTechnicianRepository = labTechnicianRepository;
    }

    public List<LabTechnician> getAll() {
        return labTechnicianRepository.findAll();
    }

    public LabTechnician getById(int id) {
        return labTechnicianRepository.findById(id)
                .orElseThrow(() -> new LabTechnicianNotFoundException(id));
    }

    public LabTechnician getByFirstNameAndLastName(String firstName, String lastName) {
        return labTechnicianRepository
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                .orElseThrow(() -> new LabTechnicianNotFoundException("Technician not found with name: " +
                        firstName + " " + lastName));
    }

    public LabTechnician getByHospitalId(int hospitalId) {
        return labTechnicianRepository.findByHospitalId(hospitalId)
                .orElseThrow(() -> new LabTechnicianNotFoundException(hospitalId));
    }

    public LabTechnician add(LabTechnician labTechnician) {
        return labTechnicianRepository.save(labTechnician);
    }

    public LabTechnician update(LabTechnician newTechnician, int id) {
        return labTechnicianRepository.findById(id)
                .map(technician -> {
                    technician.setFirstName(newTechnician.getFirstName());
                    technician.setLastName(newTechnician.getLastName());
                    if (newTechnician.getHospitalId() != 0)
                        technician.setHospitalId(newTechnician.getHospitalId());
                    if (newTechnician.getReports() != null)
                        technician.setReports(newTechnician.getReports());
                    return labTechnicianRepository.save(technician);
                }).orElseGet(() -> {
                    newTechnician.setHospitalId(id);
                    return labTechnicianRepository.save(newTechnician);
                });
    }

    public void delete(int id) {
        LabTechnician existingTechnician = getById(id);
        List<Report> reports = existingTechnician.getReports();
        if (reports != null && !reports.isEmpty()) {
            reports.forEach(report -> report.setLabTechnician(null));
        }
        labTechnicianRepository.delete(existingTechnician);
    }

}
