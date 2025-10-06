package com.gruapim.infrastructure.persistence.repositories;

import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
  Page<TaskEntity> findAll(Pageable pageable);

  Page<TaskEntity> findAllByCategory_Value(String category, Pageable pageable);
}
