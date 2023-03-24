package com.triakobah.TaskParser.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triakobah.TaskParser.core.config.JobCachingConfig;
import com.triakobah.TaskParser.core.handler.exceptions.ErrorMessages;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JobTopologyService implements JobService {

    private final TaskService taskService;

    @Autowired
    public JobTopologyService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Cacheable(value = JobCachingConfig.NAME, condition = JobCachingConfig.TASKS_MIN_SIZE_SpEL)
    public List<Task> getSortedTasks(Job job) {
        return taskService.sortTasks(job.getTasks());
    }
    @Override
    public String getCommandsScript(Job job) {
        List<Task> sortedTasks = taskService.sortTasks(job.getTasks());
        return taskService.getCommandsScript(sortedTasks);
    }

    @Override
    public String getCommandsScript(String jobJson) {
        Job job = createFrom(jobJson);
        return getCommandsScript(job);
    }

    private Job createFrom(String jobJson) {
        ObjectMapper mapper = new ObjectMapper();
        String decoded = URLDecoder.decode(jobJson, StandardCharsets.UTF_8);
        try {
            return mapper.readValue(decoded, Job.class);
        } catch (JsonProcessingException e) {
            throw new JobInputException(HttpStatus.BAD_REQUEST, ErrorMessages.JOB_INPUT_JSON_ERROR);
        }
    }

}
