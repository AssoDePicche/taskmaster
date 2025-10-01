package com.gruapim.infrastructure.configuration;

import com.gruapim.infrastructure.mappers.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataMapperConfiguration {
  @Bean
  public TaskMapper taskMapper() {
    return new TaskMapper();
  }
}
