package com.gruapim.domain;

public record Description(String value) {
  @Override
  public String toString() {
    return value;
  }
}
