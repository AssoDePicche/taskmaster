package com.gruapim.application.services;

import com.gruapim.application.dto.request.TaskPatch;
import com.gruapim.application.dto.request.TaskRequest;
import com.gruapim.application.services.exceptions.TaskNotFoundException;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import com.gruapim.infrastructure.persistence.repositories.TaskJpaRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
  private final TaskJpaRepository repository;

  public TaskService(TaskJpaRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public TaskEntity query(Long id) throws TaskNotFoundException {
    return repository.findById(id).orElseThrow(() -> new TaskNotFoundException());
  }

  public Page<TaskEntity> query(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<TaskEntity> query(Category category, Pageable pageable) {
    return repository.findAllByCategory_Value(category.toString(), pageable);
  }

  @Transactional
  public TaskEntity save(TaskEntity task) {
    return repository.save(task);
  }

  @Transactional
  public TaskEntity update(TaskEntity outdated, TaskPatch request) {
    TaskEntity task = new TaskEntity(outdated.getId(),
        Objects.requireNonNullElse(request.title(), outdated.getTitle().toString()),
        Objects.requireNonNullElse(request.category(), outdated.getCategory().toString()),
        Objects.requireNonNullElse(request.description(), outdated.getDescription().toString()),
        Objects.requireNonNullElse(request.priority(), outdated.getPriority().toInteger()),
        Objects.requireNonNullElse(
            request.deadline(), LocalDateTime.parse(outdated.getDeadline().toString())),
        Objects.requireNonNullElse(request.done(), outdated.isDone()),
        LocalDateTime.parse(outdated.getCreatedAt().toString()),
        LocalDateTime.parse(outdated.getUpdatedAt().toString()));

    repository.save(task);

    return task;
  }

  @Transactional
  public TaskEntity update(TaskEntity outdated, TaskRequest request) {
    TaskEntity task = new TaskEntity(outdated.getId(), request.title(), request.category(),
        request.description(), request.priority(), request.deadline(), outdated.isDone(),
        LocalDateTime.parse(outdated.getCreatedAt().toString()),
        LocalDateTime.parse(outdated.getUpdatedAt().toString()));

    repository.save(task);

    return task;
  }
}
