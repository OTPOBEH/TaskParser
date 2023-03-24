package com.triakobah.TaskParser.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triakobah.TaskParser.TestUtil;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.model.TaskInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

class JobTopologyServiceTests {

    @Spy
    TaskTopologyService taskService;
    @InjectMocks
    JobTopologyService jobServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGetSortedTasks() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(true);
        TaskInput dependant = job.getTasks().get(0);
        TaskInput dependable = job.getTasks().get(1);
        //endregion

        //region Action
        List<Task> result = jobServiceImpl.getSortedTasks(job);
        //endregion

        //region Assertions
        Assertions.assertEquals(result.get(0).getName(), dependable.getName());
        Assertions.assertEquals(result.get(1).getName(), dependant.getName());
        Assertions.assertEquals(2, result.size());
        //endregion
    }

    @Test
    void testGetCommandsScript() throws JsonProcessingException {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(true);
        TaskInput dependant = job.getTasks().get(0);
        TaskInput dependable = job.getTasks().get(1);
        String testValue = dependable.getCommand() + "\n" + dependant.getCommand();
        ObjectMapper mapper = new ObjectMapper();
        String jobJson = mapper.writeValueAsString(job);
        //endregion

        //region Action
        String result = jobServiceImpl.getCommandsScript(jobJson);
        //endregion

        //region Assertions
        Assertions.assertEquals(testValue, result);
        //endregion
    }

    @Test
    void testGetSortedCyclicTasksShouldThrow() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(false);
        //endregion

        //region Assertions
        Assertions.assertThrows(JobInputException.class, () -> jobServiceImpl.getSortedTasks(job));
        //endregion
    }

    @Test
    void testGetCommandsScriptCyclicTasksShouldThrow() throws JsonProcessingException {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(false);
        ObjectMapper mapper = new ObjectMapper();
        String jobJson = mapper.writeValueAsString(job);
        //endregion

        //region Assertions
        Assertions.assertThrows(JobInputException.class, () -> jobServiceImpl.getCommandsScript(jobJson));
        //endregion
    }
}
