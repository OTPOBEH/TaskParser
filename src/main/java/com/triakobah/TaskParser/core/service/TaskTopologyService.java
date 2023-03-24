package com.triakobah.TaskParser.core.service;

import com.triakobah.TaskParser.core.handler.exceptions.ErrorMessages;
import com.triakobah.TaskParser.core.handler.exceptions.GeneralJobException;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import com.triakobah.TaskParser.core.model.Task;
import com.triakobah.TaskParser.core.model.TaskInput;
import com.triakobah.TaskParser.core.service.utils.TaskTopologySorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTopologyService implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskTopologyService.class);

    public String getCommandsScript(List<Task> tasks) {
        StringBuilder stringBuilder = new StringBuilder();
        tasks.forEach(task -> stringBuilder.append(task.getCommand()).append('\n'));
        return stringBuilder.toString().trim();
    }

    public List<Task> sortTasks(List<TaskInput> unsorted) {
        try {
            LOGGER.info("START: Task Sorting");
            List<Task> sorted = TaskTopologySorter.getSortedTasks(unsorted);
            LOGGER.info("END: Task Sorting");
            return sorted;
        } catch (JobInputException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JobInputException(e);
        } catch (Exception e) {
            LOGGER.error("ERROR: Task Sorting", e);
            throw new GeneralJobException(ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }
}
