package com.example.labreportapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public final class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
