package com.gruapim.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Deadline(LocalDateTime value) {
  private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

  public Deadline {
    if (!value.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("A data limite deve ser futura");
    }
  }

  public LocalDateTime toLocalDateTime() {
    return value;
  }

  @Override
  public String toString() {
    return value.format(formatter);
  }
}
