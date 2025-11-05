package com.example.task.dto;

import com.example.task.domain.Task.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class TaskDtos {

    public record TaskRequest(
            @NotBlank @Size( max = 120 ) String title,
            @Size( max = 1000 ) String description,
            @NotNull Status status,
            @FutureOrPresent LocalDate dueDate
            ) {}

    public record TaskResponse(
            Long id,
            String title,
            String description,
            Status status,
            LocalDate dueDate
    ) {}

}
