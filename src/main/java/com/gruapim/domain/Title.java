package com.gruapim.domain;

public record Title(String value) {
  @Override
  public String toString() {
    return value;
  }
}
