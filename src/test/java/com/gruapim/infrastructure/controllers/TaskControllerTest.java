package com.gruapim.infrastructure.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gruapim.application.dto.request.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
  private static final MediaType contentType = MediaType.APPLICATION_JSON;
  private static final String URL = "/tasks/";

  @Autowired private MockMvc mvc;

  @Test
  void nonExistentTaskShouldReturnNotFoundOnDeleteMapping() throws Exception {
    Long id = 1L;

    String endpoint = URL + id;

    mvc.perform(MockMvcRequestBuilders.delete(endpoint).contentType(contentType))
        .andExpect(status().isNotFound());
  }

  @Test
  void nonExistentTaskShouldReturnNotFoundOnGetMapping() throws Exception {
    Long id = 1L;

    String endpoint = URL + id;

    mvc.perform(MockMvcRequestBuilders.get(endpoint).contentType(contentType))
        .andExpect(status().isNotFound());
  }
}
