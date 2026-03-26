package org.example.t5sr.dto.Response;

public record ErrorResponse(
        String message,
        long timestamp,
        int status
) {}