package com.gruapim.application.services;

import com.gruapim.application.dto.TaskPatch;
import com.gruapim.application.dto.TaskRequest;
import com.gruapim.application.dto.TaskResponse;
import com.gruapim.infrastructure.mappers.TaskMapper;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import com.gruapim.infrastructure.persistence.repositories.TaskJpaRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  private final TaskJpaRepository repository;

  private final TaskMapper mapper;

  public TaskService(TaskJpaRepository repository, TaskMapper mapper) {
    this.repository = repository;

    this.mapper = mapper;
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public Optional<TaskResponse> query(Long id) {
    return repository.findById(id).map((task) -> mapper.map(task));
  }

  public Page<TaskResponse> query(int page, int size, String sort) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

    return repository.findAll(pageable).map((task) -> mapper.map(task));
  }

  public TaskResponse save(TaskRequest request) {
    TaskEntity task = mapper.map(request);

    repository.save(task);

    return mapper.map(task);
  }

  public TaskResponse update(TaskResponse outdated, TaskPatch request) {
    TaskEntity task = new TaskEntity(outdated.id(),
        Objects.requireNonNullElse(request.title(), outdated.title()),
        Objects.requireNonNullElse(request.category(), outdated.category()),
        Objects.requireNonNullElse(request.description(), outdated.description()),
        Objects.requireNonNullElse(request.priority(), outdated.priority()),
        Objects.requireNonNullElse(request.deadline(), LocalDateTime.parse(outdated.deadline())),
        LocalDateTime.parse(outdated.createdAt()), LocalDateTime.parse(outdated.updatedAt()));

    repository.save(task);

    return mapper.map(task);
  }
}
