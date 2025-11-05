package com.example.task.controller;


import com.example.task.dto.TaskDtos.TaskRequest;
import com.example.task.dto.TaskDtos.TaskResponse;
import com.example.task.service.TaskService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private Long currentUserId() {
        return 1L;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        TaskResponse saved = taskService.create(currentUserId(), request);
        return ResponseEntity
                .created(URI.create("/api/v1/tasks/" + saved.id()))
                .body(saved);
    }

    @GetMapping
    public Page<TaskResponse> list(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return taskService.list(currentUserId(), status, page, size);
    }

}
