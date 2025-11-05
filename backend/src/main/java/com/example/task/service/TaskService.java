package com.example.task.service;

import com.example.task.domain.Task;
import com.example.task.domain.Task.Status;
import com.example.task.dto.TaskDtos.TaskRequest;
import com.example.task.dto.TaskDtos.TaskResponse;
import com.example.task.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    public TaskResponse create(Long userId, TaskRequest req) {
        if (req.dueDate() != null && req.dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("dueDate cannot be past");
        }

        Task entity = new Task();
        entity.setUserId(userId);
        entity.setTitle(req.title());
        entity.setDescription(req.description());
        entity.setStatus(req.status());
        entity.setDueDate(req.dueDate());

        Task saved = taskRepository.save(entity);
        return toResponse(saved);
    }

    public Page<TaskResponse> list(Long userId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Task> result;
        if (status == null || status.isBlank()) {
            result = taskRepository.findByUserId(userId, pageable);
        } else {
            Status st = Status.valueOf(status);
            result = taskRepository.findByUserIdAndStatus(userId, st, pageable);
        }
        return result.map(this::toResponse);
    }

    private TaskResponse toResponse(Task t) {
        return new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getDueDate()
        );
    }

}
