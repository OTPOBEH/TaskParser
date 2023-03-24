package com.triakobah.TaskParser.core.service;

import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.Task;

import java.util.List;

public interface JobService {
    List<Task> getSortedTasks(Job job);

    String getCommandsScript(String jobJson);

    String getCommandsScript(Job job);

}
