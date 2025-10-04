package com.gruapim.infrastructure.controllers.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
    description = "Retorna uma lista paginada de todas as tarefas ou filtradas por categoria",
    summary = "Listar tarefas",
    responses =
    { @ApiResponse(description = "Tarefas listadas com sucesso", responseCode = "200") })
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryTasks {}
