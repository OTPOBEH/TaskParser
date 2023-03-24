package com.triakobah.TaskParser.core.service.utils;

import com.triakobah.TaskParser.core.handler.exceptions.ErrorMessages;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.model.TaskInput;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskTopologySorter {

    /**
     * Employs Kahn’s algorithm for Topological Sorting of {@link TaskInput} graph
     * @param tasks List of {@link TaskInput}
     * @return Topological sorted List of {@link Task}
     * @see <a href="https://en.wikipedia.org/wiki/Topological_sorting#Algorithms">Wikipedia: Topological Sorting Algorithms</a>
     */
    public static List<Task> getSortedTasks(List<TaskInput> tasks) {
        Map<String, Set<String>> taskAdjacency = new HashMap<>();
        Map<String, TaskInput> tasksLookup = new HashMap<>();

        populateAlgoInfrastructure(tasks, taskAdjacency, tasksLookup);

        Queue<TaskInput> taskQueue = new LinkedList<>();

        // Tasks with no dependencies are added to the queue
        taskAdjacency.forEach((taskName, dependencyList) -> {
            if (dependencyList.size() == 0) {
                taskQueue.add(tasksLookup.get(taskName));
                tasksLookup.remove(taskName);
            }
        });

        List<Task> sortedTasks = new ArrayList<>();
        while (!taskQueue.isEmpty()) {
            // Dequeue task and add it to topological order
            TaskInput current = taskQueue.poll();
            sortedTasks.add(Task.createFrom(current));

            // Remove the dequeued task from all dependency lists
            removeTaskFromDependencies(taskAdjacency, tasksLookup, taskQueue, current);
        }

        if (sortedTasks.size() != tasks.size()) {
            throw new JobInputException(HttpStatus.BAD_REQUEST, ErrorMessages.JOB_INPUT_ERROR);
        }

        return sortedTasks;
    }

    private static void populateAlgoInfrastructure(List<TaskInput> tasks, Map<String, Set<String>> taskGraphMap, Map<String, TaskInput> tasksLookup) {
        tasks.forEach(task -> {
            // Add task to the look-up map
            tasksLookup.put(task.getName(), task);

            // Add task to the graph Map
            addTaskToGraph(taskGraphMap, task);
        });
    }

    private static void removeTaskFromDependencies(
            Map<String, Set<String>> taskAdjacency,
            Map<String, TaskInput> tasksLookup,
            Queue<TaskInput> taskQueue,
            Task processedTask) {

        tasksLookup.forEach((taskName, task) -> {
            if (taskAdjacency.containsKey(taskName)) {
                // Remove the processed task from all dependency lists
                taskAdjacency.get(taskName).removeIf(name -> name.equals(processedTask.getName()));

                // If a task has no dependencies left add it to the queue for processing
                if (taskAdjacency.get(taskName).size() == 0) {
                    taskQueue.add(tasksLookup.get(taskName));

                    // Task is enqueued for processing, so it should be removed from the graph map
                    taskAdjacency.remove(taskName);
                }
            }
        });
    }

    private static void addTaskToGraph(Map<String, Set<String>> taskGraphMap, TaskInput task) {
        //Add task node
        if (!taskGraphMap.containsKey(task.getName())) {
            taskGraphMap.put(task.getName(), new HashSet<>());
        }

        // Enlist dependencies (Create vertices)
        if (task.getRequires() != null) {
            task.getRequires().forEach(dependency -> taskGraphMap.get(task.getName()).add(dependency));
        }
    }

}
