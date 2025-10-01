package com.gruapim.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record TaskRequest(@NotBlank String title, @NotBlank String category,
    @NotBlank String description, @Min(0) Integer priority, @Future LocalDateTime deadline) {}
