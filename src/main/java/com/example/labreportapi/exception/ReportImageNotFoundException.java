package com.example.labreportapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReportImageNotFoundException extends RuntimeException {

    public ReportImageNotFoundException(int id) {
        super("Report image not found with report id: " + id);
    }

}
