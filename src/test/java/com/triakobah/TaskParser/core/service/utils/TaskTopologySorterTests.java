package com.triakobah.TaskParser.core.service.utils;

import com.triakobah.TaskParser.TestUtil;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.model.TaskInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TaskTopologySorterTests {

    @Test
    void testGetSortedTasks() {
        //region Setup
        Job job = TestUtil.getTwoTasksJob(true);
        TaskInput dependant = job.getTasks().get(0);
        TaskInput dependable = job.getTasks().get(1);
        //endregion

        //region Action
        List<Task> result = TaskTopologySorter.getSortedTasks(job.getTasks());
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
        Assertions.assertThrows(JobInputException.class, () -> TaskTopologySorter.getSortedTasks(job.getTasks()));
        //endregion
    }

}
