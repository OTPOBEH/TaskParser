package com.triakobah.TaskParser.core.constants;

public final class ApiConstants {

    public static final String REQUEST_ID = "id";

    public static class PathParams {
        public static final String API_JOBS = "/api/jobs/";
        public static final String V1 = "v1";
        public static final String V2 = "v2";
        public static final String TASKS = "tasks";
        public static final String COMMANDS = "commands";

    }

    private ApiConstants() {
        //should not be instantiated
    }
}
