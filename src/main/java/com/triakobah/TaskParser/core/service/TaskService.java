package com.triakobah.TaskParser.core.service;

import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.model.TaskInput;

import java.util.List;

public interface TaskService {
    String getCommandsScript(List<Task> tasks);

    List<Task> sortTasks(List<TaskInput> unsorted);
}
