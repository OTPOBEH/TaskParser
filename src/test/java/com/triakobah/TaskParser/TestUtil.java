package com.triakobah.TaskParser;

import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.model.TaskInput;

import java.util.List;

public class TestUtil {
    public static Job getTwoTasksJob(boolean nonCyclic) {
        TaskInput dependant = new TaskInput("dependant", "command-1", List.of("dependable"));
        TaskInput dependable = new TaskInput("dependable", "command-2", nonCyclic ? null : List.of("dependant"));
        return new Job(List.of(dependant, dependable));
    }
}
