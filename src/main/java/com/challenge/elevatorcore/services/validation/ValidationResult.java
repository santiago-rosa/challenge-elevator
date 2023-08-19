package com.challenge.elevatorcore.services.validation;

public class ValidationResult {

    public String cause;
    public boolean error;

    public ValidationResult(String cause, boolean error) {
        this.cause = cause;
        this.error = error;
    }
}
