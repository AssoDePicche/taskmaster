package com.gruapim.adapters.controllers;

import com.gruapim.adapters.controllers.exceptions.NotFoundException;
import com.gruapim.application.dto.TaskPatch;
import com.gruapim.application.dto.TaskRequest;
import com.gruapim.application.dto.TaskResponse;
import com.gruapim.application.services.TaskService;
import com.gruapim.domain.Category;
import com.gruapim.infrastructure.mappers.TaskMapper;
import com.gruapim.infrastructure.persistence.entities.TaskEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
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
  @Operation(description = "Remove permanentemente uma tarefa", summary = "Excluir tarefa",
      responses =
      {
        @ApiResponse(description = "Tarefa excluída com sucesso", responseCode = "204")
        , @ApiResponse(description = "Tarefa não encontrada", responseCode = "404")
      })
  public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
    TaskEntity task = service.query(id).orElseThrow(() -> new NotFoundException());

    service.delete(task.getId());

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(
      description = "Retorna uma lista paginada de todas as tarefas ou filtradas por categoria",
      summary = "Listar tarefas",
      responses =
      { @ApiResponse(description = "Tarefas listadas com sucesso", responseCode = "200") })
  public ResponseEntity<Page<TaskResponse>>
  get(@Parameter(description = "Filtra tarefas por categoria (opcional)", name = "categoria",
          required = false) @RequestParam(name = "categoria", required = false) Category category,
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<TaskEntity> entities =
        (null != category) ? service.query(category, pageable) : service.query(pageable);

    Page<TaskResponse> response = entities.map(mapper::map);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  @Operation(description = "Retorna os detalhes de uma tarefa", summary = "Buscar tarefa por id",
      responses =
      {
        @ApiResponse(description = "Tarefa encontrada com sucesso", responseCode = "200")
        , @ApiResponse(description = "Tarefa não encontrada", responseCode = "404")
      })
  public ResponseEntity<TaskResponse>
  get(@PathVariable Long id) throws Exception {
    TaskEntity entity = service.query(id).orElseThrow(() -> new NotFoundException());

    TaskResponse response = mapper.map(entity);

    return ResponseEntity.ok().body(response);
  }

  @PatchMapping("/{id}")
  @Operation(description = "Atualiza parcialmente os detalhes de uma tarefa",
      summary = "Atualizar parcialmente uma tarefa",
      responses =
      {
        @ApiResponse(description = "Tarefa atualizada com sucesso", responseCode = "200")
        , @ApiResponse(description = "Tarefa não encontrada", responseCode = "404")
      })
  public ResponseEntity<TaskResponse>
  patch(@PathVariable Long id, @Valid @RequestBody TaskPatch request) throws Exception {
    TaskEntity entity = service.query(id).orElseThrow(() -> new NotFoundException());

    TaskResponse response = mapper.map(service.update(entity, request));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  @Operation(description = "Cria uma tarefa", summary = "Criar tarefa",
      responses =
      {
        @ApiResponse(description = "Tarefa criada com sucesso", responseCode = "201")
        , @ApiResponse(description = "Dados inválidos", responseCode = "400")
      })
  public ResponseEntity<TaskResponse>
  post(@Valid @RequestBody TaskRequest request) throws Exception {
    TaskEntity entity = mapper.map(request);

    TaskResponse response = mapper.map(service.save(entity));

    String path = String.format("/tasks/%d", response.id());

    URI uri = new URI(path);

    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{id}")
  @Operation(description = "Atualiza todos os detalhes de uma tarefa", summary = "Atualizar tarefa",
      responses =
      {
        @ApiResponse(description = "Tarefa atualizada com sucesso", responseCode = "200")
        , @ApiResponse(description = "Tarefa não encontrada", responseCode = "404")
      })
  public ResponseEntity<TaskResponse>
  put(@PathVariable Long id, @Valid @RequestBody TaskRequest request) throws Exception {
    TaskEntity entity = service.query(id).orElseThrow(() -> new NotFoundException());

    TaskResponse response = mapper.map(service.update(entity, request));

    return ResponseEntity.ok().body(response);
  }
}
