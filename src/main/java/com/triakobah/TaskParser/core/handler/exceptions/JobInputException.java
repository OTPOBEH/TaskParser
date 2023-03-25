package com.triakobah.TaskParser.core.handler.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.triakobah.TaskParser.core.constants.ApiConstants;
import lombok.*;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JobInputException extends ApiException {

    List<String> validationErrors;

    public JobInputException(HttpStatus status, String message) {
        super(message);
        super.requestId = MDC.get(ApiConstants.REQUEST_ID);
        super.status = status;
    }

    public JobInputException(JobInputException exception) {
        super(exception.getMessage());
        this.requestId = exception.getRequestId();
        this.status = exception.getStatus();
        this.validationErrors = exception.validationErrors;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return "JobInputException{" +
                "requestId='" + requestId + '\'' +
                ", message='" + super.getMessage() + '\'' +
                ", status=" + status +
                '}';
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
