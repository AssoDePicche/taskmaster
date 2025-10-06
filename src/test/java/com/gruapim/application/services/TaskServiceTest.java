package com.gruapim.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gruapim.application.dto.request.TaskPatch;
import com.gruapim.application.dto.request.TaskRequest;
import com.gruapim.application.services.exceptions.TaskNotFoundException;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import com.gruapim.infrastructure.persistence.repositories.TaskJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class TaskServiceTest {
  @Mock private TaskJpaRepository repository;

  @InjectMocks private TaskService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deleteShouldCallRepositoryDeleteById() {
    Long taskId = 1L;

    service.delete(taskId);

    verify(repository, times(1)).deleteById(taskId);
  }

  @Test
  void queryByIdShouldReturnTaskWhenExists() {
    TaskEntity mockTask = mock(TaskEntity.class);

    when(repository.findById(1L)).thenReturn(Optional.of(mockTask));

    TaskEntity result = service.query(1L);

    assertEquals(mockTask, result);
  }

  @Test
  void queryByIdShouldThrowTaskNotFoundExceptionWhenNotFound() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> service.query(1L));
  }

  @Test
  void queryWithPageableShouldReturnPage() {
    Pageable pageable = mock(Pageable.class);

    Page<TaskEntity> mockPage = new PageImpl<>(List.of(mock(TaskEntity.class)));

    when(repository.findAll(pageable)).thenReturn(mockPage);

    Page<TaskEntity> result = service.query(pageable);

    assertEquals(mockPage, result);
  }

  @Test
  void queryWithCategoryAndPageableShouldReturnPage() {
    Pageable pageable = mock(Pageable.class);

    Category category = new Category("WORK");

    Page<TaskEntity> mockPage = new PageImpl<>(List.of(mock(TaskEntity.class)));

    when(repository.findAllByCategory_Value(category.toString(), pageable)).thenReturn(mockPage);

    Page<TaskEntity> result = service.query(category, pageable);

    assertEquals(mockPage, result);
  }

  @Test
  void saveShouldReturnSavedTask() {
    TaskEntity task = mock(TaskEntity.class);

    when(repository.save(task)).thenReturn(task);

    TaskEntity result = service.save(task);

    assertEquals(task, result);
  }

  @Test
  void updateWithTaskPatchShouldUpdateFields() {
    TaskEntity outdated = new TaskEntity(1L, "Old Title", "WORK", "Old Desc", 1,
        LocalDateTime.now().plusDays(1), false, LocalDateTime.now(), LocalDateTime.now());

    TaskPatch patch = new TaskPatch(
        "New Title", "PERSONAL", "New Desc", 2, LocalDateTime.now().plusDays(2), true);

    TaskEntity savedTask = mock(TaskEntity.class);

    when(repository.save(any(TaskEntity.class))).thenReturn(savedTask);

    TaskEntity result = service.update(outdated, patch);

    assertNotNull(result);

    verify(repository).save(any(TaskEntity.class));
  }

  @Test
  void updateWithTaskRequestShouldReplaceFields() {
    TaskEntity outdated = new TaskEntity(1L, "Old Title", "WORK", "Old Desc", 1,
        LocalDateTime.now().plusDays(1), false, LocalDateTime.now(), LocalDateTime.now());

    TaskRequest request =
        new TaskRequest("New Title", "PERSONAL", "New Desc", 3, LocalDateTime.now().plusDays(2));

    TaskEntity savedTask = mock(TaskEntity.class);

    when(repository.save(any(TaskEntity.class))).thenReturn(savedTask);

    TaskEntity result = service.update(outdated, request);

    assertNotNull(result);

    verify(repository).save(any(TaskEntity.class));
  }
}
