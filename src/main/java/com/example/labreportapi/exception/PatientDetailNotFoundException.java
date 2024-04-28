package com.example.labreportapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PatientDetailNotFoundException extends RuntimeException {

    public PatientDetailNotFoundException(int id) {
        super("Patient detail not found with patient id: " + id);
    }
}
