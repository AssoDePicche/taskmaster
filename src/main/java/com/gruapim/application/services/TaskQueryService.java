package com.gruapim.application.services;

import com.gruapim.application.services.exceptions.TaskNotFoundException;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import com.gruapim.infrastructure.persistence.repositories.TaskJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskQueryService {
  private final TaskJpaRepository repository;

  public TaskQueryService(TaskJpaRepository repository) {
    this.repository = repository;
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
}
