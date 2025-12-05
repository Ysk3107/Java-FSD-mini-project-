package com.wazooinc.springboottodoapplication.controllers;

import com.wazooinc.springboottodoapplication.models.TodoItem;
import com.wazooinc.springboottodoapplication.repositories.TodoItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Controller
public class TodoFormController {
    
    @Autowired
    private TodoItemRepository todoItemRepository;
    
    @Autowired
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "add-todo-item";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
    
        model.addAttribute("todo", todoItem);
        return "update-todo-item";
    }

    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") long id, Model model) {
        // Delete the todo item
        TodoItem todoItem = todoItemRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
        todoItemRepository.delete(todoItem);
        
        // Check if this was the last item and reset sequence if needed
        if (todoItemRepository.count() == 0) {
            resetAutoIncrement();
        }
        
        return "redirect:/";
    }
    
    private void resetAutoIncrement() {
        // For H2 database
        jdbcTemplate.execute("ALTER TABLE TODO_ITEM ALTER COLUMN ID RESTART WITH 1");
        
        // If the above doesn't work, try this alternative:
        // jdbcTemplate.execute("DROP SEQUENCE IF EXISTS TODO_ITEM_SEQ");
        // jdbcTemplate.execute("CREATE SEQUENCE TODO_ITEM_SEQ START WITH 1 INCREMENT BY 1");
    }
}
