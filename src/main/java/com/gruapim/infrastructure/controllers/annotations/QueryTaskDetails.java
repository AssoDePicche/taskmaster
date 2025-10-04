package com.gruapim.infrastructure.controllers.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(description = "Retorna os detalhes de uma tarefa", summary = "Buscar tarefa por id",
    responses =
    {
      @ApiResponse(description = "Tarefa encontrada com sucesso", responseCode = "200")
      , @ApiResponse(description = "Tarefa não encontrada", responseCode = "404")
    })
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryTaskDetails {}
