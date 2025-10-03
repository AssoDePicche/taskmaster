package com.gruapim.application.dto;

import java.time.LocalDateTime;

public record TaskPatch(String title, String category, String description, Integer priority,
    LocalDateTime deadline, Boolean done) {}
