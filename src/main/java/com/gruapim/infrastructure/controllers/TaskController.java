package com.gruapim.infrastructure.controllers;

import com.gruapim.application.dto.TaskPatch;
import com.gruapim.application.dto.TaskRequest;
import com.gruapim.application.dto.TaskResponse;
import com.gruapim.application.services.TaskPersistenceService;
import com.gruapim.application.services.TaskQueryService;
import com.gruapim.application.services.TaskUpdateService;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.controllers.annotations.CategoryQuery;
import com.gruapim.infrastructure.controllers.annotations.CreateTask;
import com.gruapim.infrastructure.controllers.annotations.DeleteTask;
import com.gruapim.infrastructure.controllers.annotations.PartiallyUpdateTask;
import com.gruapim.infrastructure.controllers.annotations.QueryTaskDetails;
import com.gruapim.infrastructure.controllers.annotations.QueryTasks;
import com.gruapim.infrastructure.controllers.annotations.UpdateTask;
import com.gruapim.infrastructure.mappers.TaskMapper;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@Tag(description = "API para gerenciamento de tarefas", name = "Tarefas")
@Validated
public class TaskController {
  private final TaskMapper mapper;

  private final TaskPersistenceService persistenceService;

  private final TaskUpdateService updateService;

  private final TaskQueryService queryService;

  public TaskController(TaskMapper mapper, TaskPersistenceService persistenceService,
      TaskUpdateService updateService, TaskQueryService queryService) {
    this.mapper = mapper;

    this.persistenceService = persistenceService;

    this.updateService = updateService;

    this.queryService = queryService;
  }

  @DeleteMapping("/{id}")
  @DeleteTask
  public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
    TaskEntity task = queryService.query(id);

    persistenceService.delete(task.getId());

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @QueryTasks
  public ResponseEntity<Page<TaskResponse>> get(
      @CategoryQuery @RequestParam(name = "categoria", required = false) Category category,
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<TaskEntity> entities;

    if (null != category) {
      entities = queryService.query(category, pageable);
    } else {
      entities = queryService.query(pageable);
    }

    Page<TaskResponse> response = entities.map(mapper::map);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  @QueryTaskDetails
  public ResponseEntity<TaskResponse> get(@PathVariable Long id) throws Exception {
    TaskEntity entity = queryService.query(id);

    TaskResponse response = mapper.map(entity);

    return ResponseEntity.ok().body(response);
  }

  @PatchMapping("/{id}")
  @PartiallyUpdateTask
  public ResponseEntity<TaskResponse> patch(
      @PathVariable Long id, @Valid @RequestBody TaskPatch request) throws Exception {
    TaskEntity entity = queryService.query(id);

    TaskResponse response = mapper.map(updateService.update(entity, request));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  @CreateTask
  public ResponseEntity<TaskResponse> post(@Valid @RequestBody TaskRequest request)
      throws Exception {
    TaskEntity entity = mapper.map(request);

    TaskResponse response = mapper.map(persistenceService.save(entity));

    String path = String.format("/tasks/%d", response.id());

    URI uri = new URI(path);

    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{id}")
  @UpdateTask
  public ResponseEntity<TaskResponse> put(
      @PathVariable Long id, @Valid @RequestBody TaskRequest request) throws Exception {
    TaskEntity entity = queryService.query(id);

    TaskResponse response = mapper.map(updateService.update(entity, request));

    return ResponseEntity.ok().body(response);
  }
}
