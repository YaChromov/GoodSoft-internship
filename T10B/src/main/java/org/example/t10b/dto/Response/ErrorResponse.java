package org.example.t10b.dto.Response;

public record ErrorResponse(
        String message,
        long timestamp,
        int status
) {}