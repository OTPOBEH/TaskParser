package com.triakobah.TaskParser.core.service;

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

import java.util.Arrays;
import java.util.List;

class TaskTopologyServiceTests {
    @InjectMocks
    TaskTopologyService taskTopologyService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGetCommandsScript() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(true);
        Task dependant = job.getTasks().get(0);
        Task dependable = job.getTasks().get(1);
        List<Task> tasks = Arrays.asList(dependant, dependable);
        String testValue = dependant.getCommand() + "\n" + dependable.getCommand();
        //endregion

        //region Action
        String result = taskTopologyService.getCommandsScript(tasks);
        //endregion

        //region Assertions
        Assertions.assertEquals(testValue, result);
        //endregion
    }

    @Test
    void testSortTasks() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(true);
        TaskInput dependant = job.getTasks().get(0);
        TaskInput dependable = job.getTasks().get(1);
        //endregion

        //region Action
        List<Task> result = taskTopologyService.sortTasks(job.getTasks());
        //endregion

        //region Assertions
        Assertions.assertEquals(result.get(0).getName(), dependable.getName());
        Assertions.assertEquals(result.get(1).getName(), dependant.getName());
        Assertions.assertEquals(2, result.size());
        //endregion
    }

    @Test
    void testGetSortedCyclicTasksShouldThrow() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(false);
        //endregion

        //region Assertions
        Assertions.assertThrows(JobInputException.class, () -> taskTopologyService.sortTasks(job.getTasks()));
        //endregion
    }

}
