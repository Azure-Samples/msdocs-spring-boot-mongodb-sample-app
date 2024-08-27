// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.microsoft.azure.appservice.examples.springbootmongodb.dao.TodoItemRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class TodoListController {

    private static Logger logger = LoggerFactory.getLogger(TodoListController.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    public TodoListController() {
    }

    /**
     * HTTP GET
     */
    @GetMapping(path = "/api/todolist/{index}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TodoItem getTodoItem(@PathVariable("index") String index) {
        logger.info("GET request access '/api/todolist/{}' path.", index);
        return todoItemRepository.findById(index).get();
    }

    /**
     * HTTP GET ALL
     */
    @GetMapping(path = "/api/todolist", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TodoItem> getAllTodoItems() {
        logger.info("GET request access '/api/todolist' path.");

        List<TodoItem> items = todoItemRepository.findAll();
        return todoItemRepository.findAll();
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/todolist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addNewTodoItem(@RequestBody TodoItem item) {
        logger.info("POST request access '/api/todolist' path with item: {}", item);
        try {
            item.setId(UUID.randomUUID().toString());
            todoItemRepository.save(item);
            return "Todo item created";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo item creation failed");
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(path = "/api/todolist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateTodoItem(@RequestBody TodoItem item) {
        logger.info("PUT request access '/api/todolist' path with item {}", item);
        try {
            todoItemRepository.deleteById(item.getId());
            todoItemRepository.save(item);
            return "Todo item updated";
        } catch (Exception e) {
            logger.error("Update errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo item not found");
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping("/api/todolist/{id}")
    public String deleteTodoItem(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/todolist/{}' path.", id);
        try {
            todoItemRepository.deleteById(id);
            return "Todo item deleted";
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo item not found");
        }

    }
}
