package org.example.t10b.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword
) {}