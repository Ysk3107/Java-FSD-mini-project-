package com.wazooinc.springboottodoapplication.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private boolean completed;

    private Instant createdDate;

    private Instant modifiedDate;

    // Default constructor
    public TodoItem() {
    }

    // Constructor with title
    public TodoItem(String title) {
        this.title = title;
        this.completed = false;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return String.format(
            "TodoItem{id=%d, title='%s', completed='%s', createdDate='%s', modifiedDate='%s'}",
            id, title, completed, createdDate, modifiedDate
        );
    }
}
