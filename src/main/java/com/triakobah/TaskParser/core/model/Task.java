package com.triakobah.TaskParser.core.model;

import com.triakobah.TaskParser.core.handler.exceptions.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @NotBlank(message = ErrorMessages.TASK_NAME_EMPTY)
    private String name;

    @NotBlank(message = ErrorMessages.TASK_COMMAND_EMPTY)
    private String command;

    public static Task createFrom(TaskInput taskInput) {
        Task task = Task.builder()
                .name(taskInput.getName())
                .command(taskInput.getCommand()).build();
        return task;
    }
}
