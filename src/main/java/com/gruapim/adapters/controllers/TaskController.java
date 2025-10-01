package com.gruapim.adapters.controllers;

import com.gruapim.adapters.controllers.exceptions.NotFoundException;
import com.gruapim.application.dto.TaskPatch;
import com.gruapim.application.dto.TaskRequest;
import com.gruapim.application.dto.TaskResponse;
import com.gruapim.application.services.TaskService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
    TaskResponse task = queryOrThrowNotFound(id);

    service.delete(task.id());

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<TaskResponse>> get(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
    Page<TaskResponse> response = service.query(page, size, sort);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> get(@PathVariable Long id) throws Exception {
    TaskResponse response = queryOrThrowNotFound(id);

    return ResponseEntity.ok().body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TaskResponse> patch(
      @PathVariable Long id, @Valid @RequestBody TaskPatch request) throws Exception {
    TaskResponse task = queryOrThrowNotFound(id);

    TaskResponse response = service.update(task, request);

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<TaskResponse> post(@Valid @RequestBody TaskRequest request)
      throws Exception {
    TaskResponse response = service.save(request);

    String path = String.format("/tasks/%d", response.id());

    URI uri = new URI(path);

    return ResponseEntity.created(uri).body(response);
  }

  private TaskResponse queryOrThrowNotFound(Long id) throws Exception {
    Optional<TaskResponse> task = service.query(id);

    if (!task.isPresent()) {
      throw new NotFoundException();
    }

    return task.get();
  }
}
