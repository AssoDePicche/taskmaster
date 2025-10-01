package com.gruapim.domain;

import java.time.LocalDateTime;

public record Deadline(LocalDateTime value) {
  public Deadline {
    if (!value.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("A data limite deve ser futura");
    }
  }

  public LocalDateTime toLocalDateTime() {
    return value;
  }
}
