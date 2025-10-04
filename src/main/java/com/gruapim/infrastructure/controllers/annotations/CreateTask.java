package com.gruapim.infrastructure.controllers.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(description = "Cria uma tarefa", summary = "Criar tarefa",
    responses =
    {
      @ApiResponse(description = "Tarefa criada com sucesso", responseCode = "201")
      , @ApiResponse(description = "Dados inválidos", responseCode = "400")
    })
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CreateTask {}
