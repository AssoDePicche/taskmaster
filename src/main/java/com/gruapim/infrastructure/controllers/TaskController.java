package com.gruapim.infrastructure.controllers;

import com.gruapim.application.dto.request.TaskPatch;
import com.gruapim.application.dto.request.TaskRequest;
import com.gruapim.application.dto.response.TaskResponse;
import com.gruapim.application.services.TaskService;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.controllers.annotations.CategoryParam;
import com.gruapim.infrastructure.controllers.annotations.CreatedTaskDocs;
import com.gruapim.infrastructure.controllers.annotations.DeletedTaskDocs;
import com.gruapim.infrastructure.controllers.annotations.PaginatedTasksDocs;
import com.gruapim.infrastructure.controllers.annotations.PartiallyUpdatedTaskDocs;
import com.gruapim.infrastructure.controllers.annotations.QueriedTaskDocs;
import com.gruapim.infrastructure.controllers.annotations.UpdatedTaskDocs;
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

  private final TaskService service;

  public TaskController(TaskMapper mapper, TaskService service) {
    this.mapper = mapper;

    this.service = service;
  }

  @DeleteMapping("/{id}")
  @DeletedTaskDocs
  public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
    TaskEntity task = service.query(id);

    service.delete(task.getId());

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @PaginatedTasksDocs
  public ResponseEntity<Page<TaskResponse>> get(
      @CategoryParam @RequestParam(name = "categoria", required = false) Category category,
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<TaskEntity> entities;

    if (null != category) {
      entities = service.query(category, pageable);
    } else {
      entities = service.query(pageable);
    }

    Page<TaskResponse> response = entities.map(mapper::map);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  @QueriedTaskDocs
  public ResponseEntity<TaskResponse> get(@PathVariable Long id) throws Exception {
    TaskEntity entity = service.query(id);

    TaskResponse response = mapper.map(entity);

    return ResponseEntity.ok().body(response);
  }

  @PatchMapping("/{id}")
  @PartiallyUpdatedTaskDocs
  public ResponseEntity<TaskResponse> patch(
      @PathVariable Long id, @Valid @RequestBody TaskPatch request) throws Exception {
    TaskEntity entity = service.query(id);

    TaskResponse response = mapper.map(service.update(entity, request));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  @CreatedTaskDocs
  public ResponseEntity<TaskResponse> post(@Valid @RequestBody TaskRequest request)
      throws Exception {
    TaskEntity entity = mapper.map(request);

    TaskResponse response = mapper.map(service.save(entity));

    String path = String.format("/tasks/%d", response.id());

    URI uri = new URI(path);

    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{id}")
  @UpdatedTaskDocs
  public ResponseEntity<TaskResponse> put(
      @PathVariable Long id, @Valid @RequestBody TaskRequest request) throws Exception {
    TaskEntity entity = service.query(id);

    TaskResponse response = mapper.map(service.update(entity, request));

    return ResponseEntity.ok().body(response);
  }
}
