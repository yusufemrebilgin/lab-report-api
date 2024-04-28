package com.example.labreportapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReportDetailNotFoundException extends RuntimeException {

    public ReportDetailNotFoundException(int id) {
        super("Report detail not found with report id: " + id);
    }

}
