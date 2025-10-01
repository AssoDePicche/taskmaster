package com.gruapim.application.dto;

public record TaskResponse(Long id, String title, String category, String description,
    Integer priority, String deadline, String createdAt, String updatedAt) {}
