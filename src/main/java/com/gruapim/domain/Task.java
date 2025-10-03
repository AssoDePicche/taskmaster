package com.gruapim.domain;

import java.time.LocalDateTime;

public interface Task {
  Long getId();

  Title getTitle();

  Category getCategory();

  Description getDescription();

  Priority getPriority();

  Deadline getDeadline();

  Boolean isDone();

  LocalDateTime getCreatedAt();

  LocalDateTime getUpdatedAt();
}
