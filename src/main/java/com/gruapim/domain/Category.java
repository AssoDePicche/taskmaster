package com.gruapim.domain;

public record Category(String value) {
  @Override
  public String toString() {
    return value;
  }
}
