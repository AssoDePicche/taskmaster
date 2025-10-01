package com.gruapim.infrastructure.persistence.entities;

import com.gruapim.domain.Category;
import com.gruapim.domain.Deadline;
import com.gruapim.domain.Description;
import com.gruapim.domain.Priority;
import com.gruapim.domain.Task;
import com.gruapim.domain.Title;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks")
public class TaskEntity implements Task {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @AttributeOverride(name = "value", column = @Column(name = "title"))
  @Embedded
  private Title title;

  @AttributeOverride(name = "value", column = @Column(name = "category"))
  @Embedded
  private Category category;

  @AttributeOverride(name = "value", column = @Column(name = "description"))
  @Embedded
  private Description description;

  @AttributeOverride(name = "value", column = @Column(name = "priority"))
  @Embedded
  private Priority priority;

  @AttributeOverride(name = "value", column = @Column(name = "deadline"))
  @Embedded
  private Deadline deadline;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;

  public TaskEntity() {}

  public TaskEntity(
      String title, String category, String description, Integer priority, LocalDateTime deadline) {
    this(null, title, category, description, priority, deadline, null, null);
  }

  public TaskEntity(Long id, String title, String category, String description, Integer priority,
      LocalDateTime deadline, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;

    this.title = new Title(title);

    this.category = new Category(category);

    this.description = new Description(description);

    this.priority = new Priority(priority);

    this.deadline = new Deadline(deadline);

    this.createdAt = createdAt;

    this.updatedAt = updatedAt;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Title getTitle() {
    return title;
  }

  @Override
  public Category getCategory() {
    return category;
  }

  @Override
  public Description getDescription() {
    return description;
  }

  @Override
  public Priority getPriority() {
    return priority;
  }

  @Override
  public Deadline getDeadline() {
    return deadline;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
