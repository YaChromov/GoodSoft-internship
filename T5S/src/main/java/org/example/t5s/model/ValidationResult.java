package org.example.t5s.model;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private final Map<String, String> errors = new HashMap<>();

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}