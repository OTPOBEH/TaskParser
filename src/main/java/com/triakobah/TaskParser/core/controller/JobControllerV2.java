package com.triakobah.TaskParser.core.controller;

import com.triakobah.TaskParser.core.constants.ApiConstants;
import com.triakobah.TaskParser.core.model.Job;
import com.triakobah.TaskParser.core.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**TODO: Un-hide feature flag if/when change of contract is acceptable so to set the cUrl content type to "application/json".
 * The above change must be addressed also in the <a href="file:/resources/static/open-api.json">open-api.json</a>
*/
@ConditionalOnExpression(value = "${job.controller.v2.visible}")
@RestController
@RequestMapping(ApiConstants.PathParams.API_JOBS + ApiConstants.PathParams.V2)
public class JobControllerV2 {

    private final JobService jobService;

    @Autowired
    public JobControllerV2(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping(path = ApiConstants.PathParams.COMMANDS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String solveJobTasks2(@Valid @RequestBody Job job) {
        return jobService.getCommandsScript(job);
    }

}
