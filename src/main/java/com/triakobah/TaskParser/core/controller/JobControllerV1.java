package com.triakobah.TaskParser.core.controller;

import com.triakobah.TaskParser.core.constants.ApiConstants;
import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.PathParams.API_JOBS + ApiConstants.PathParams.V1)
public class JobControllerV1 {

    private final JobService jobService;

    @Autowired
    public JobControllerV1(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping(path = ApiConstants.PathParams.TASKS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> solveJobTasks(@Valid @RequestBody Job job) {
        return jobService.getSortedTasks(job);
    }

    @PostMapping(path = ApiConstants.PathParams.COMMANDS,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String getCommandsScript(@RequestBody String jobJson) {
        return jobService.getCommandsScript(jobJson);
    }

}
