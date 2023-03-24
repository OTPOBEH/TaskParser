package com.triakobah.TaskParser.core.handler.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralJobException extends RuntimeException {
    public GeneralJobException(String exception) {
        super(exception);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
