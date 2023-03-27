package com.triakobah.TaskParser.core.handler.exceptions;

public final class ErrorMessages {
    public static final String TASK_NAME_EMPTY = "Task name may not be empty";
    public static final String TASK_COMMAND_EMPTY = "Command may not be empty";
    public static final String JOB_INPUT_ERROR = """
            Input data problem. Possible cause:
             - cyclic dependency;
             - unsatisfiable requirement.""";

    public static final String JOB_INPUT_JSON_ERROR = """
            Can not read JSON resource.
            Source file requirements:
            - must be UTF-8 encoded.
            - must contain valid JSON string.
            """;

    public static final String INTERNAL_SERVER_ERROR = "Something went wrong. " +
            "Please, try again and if the problem persists contact your administrator.";

    public static final String VALIDATION_ERROR = "Validation error(s). Please check your input.";
    public static final String DUPLICATE_TASK_NAME = "Task name must be unique.";

    private ErrorMessages() {
        //should not be instantiated
    }
}