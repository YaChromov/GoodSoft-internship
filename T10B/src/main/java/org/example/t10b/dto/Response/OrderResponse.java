package org.example.t10b.dto.Response;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String clientUsername,
        String adminUsername,
        Integer capacity,
        String apartmentClass,
        Integer stayDays,
        String status,
        boolean paid,
        LocalDateTime createdAt,
        LocalDateTime processedAt
) {}