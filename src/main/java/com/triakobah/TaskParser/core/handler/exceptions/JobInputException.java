package com.triakobah.TaskParser.core.handler.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.triakobah.TaskParser.core.constants.ApiConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JobInputException extends RuntimeException {
    public String requestId;
    HttpStatus status;

    public JobInputException(HttpStatus status, String message) {
        super(message);
        this.requestId = MDC.get(ApiConstants.REQUEST_ID);
        this.status = status;
    }

    public JobInputException(JobInputException exception) {
        super(exception.getMessage());
        this.requestId = exception.getRequestId();
        this.status = exception.getStatus();
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
