package com.example.labreportapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LabTechnicianNotFoundException extends RuntimeException {

    public LabTechnicianNotFoundException(String message) {
        super(message);
    }

    public LabTechnicianNotFoundException(int id) {
        super("Lab Technician not found with id " + id);
    }

}
