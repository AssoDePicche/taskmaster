package com.gruapim.application.services;

import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import com.gruapim.infrastructure.persistence.repositories.TaskJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskPersistenceService {
  private final TaskJpaRepository repository;

  public TaskPersistenceService(TaskJpaRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public TaskEntity save(TaskEntity task) {
    return repository.save(task);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
