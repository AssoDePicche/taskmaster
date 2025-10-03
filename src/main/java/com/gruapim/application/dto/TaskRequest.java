package com.gruapim.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record TaskRequest(@NotBlank(message = "O título deve ser preenchido") String title,
    @NotBlank(message = "A categoria deve ser preenchida") String category,
    @NotBlank(message = "A descrição deve ser preenchida") String description,
    @Min(value = 0, message = "A prioridade deve ser maior ou igual a zero") Integer priority,
    @Future(message = "A data de conclusão deve ser futura") LocalDateTime deadline) {}
