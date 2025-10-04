package com.gruapim.application.dto.response;

public record TaskResponse(Long id, String title, String category, String description,
    Integer priority, String deadline, Boolean done, String createdAt, String updatedAt) {}
