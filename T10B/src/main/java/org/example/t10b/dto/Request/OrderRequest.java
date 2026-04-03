package org.example.t10b.dto.Request;

public record OrderRequest(
        Integer capacity,
        String apartmentClass,
        Integer stayDays
) {}