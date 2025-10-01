package com.gruapim.domain;

public record Priority(Integer value) {
  public Priority {
    if (value < 0) {
      throw new IllegalArgumentException("A prioridade deve ser maior ou igual a zero");
    }
  }

  public Integer toInteger() {
    return value;
  }
}
