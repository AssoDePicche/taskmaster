package com.gruapim.infrastructure.mappers;

import com.gruapim.application.dto.request.TaskRequest;
import com.gruapim.application.dto.response.TaskResponse;
import com.gruapim.domain.Task;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import java.time.format.DateTimeFormatter;

public class TaskMapper {
  private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

  public TaskEntity map(TaskRequest request) {
    return new TaskEntity(request.title(), request.category(), request.description(),
        request.priority(), request.deadline());
  }

  public TaskResponse map(Task task) {
    return new TaskResponse(task.getId(), task.getTitle().toString(), task.getCategory().toString(),
        task.getDescription().toString(), task.getPriority().toInteger(),
        task.getDeadline().toLocalDateTime().format(formatter), task.isDone(),
        task.getCreatedAt().format(formatter), task.getUpdatedAt().format(formatter));
  }
}
