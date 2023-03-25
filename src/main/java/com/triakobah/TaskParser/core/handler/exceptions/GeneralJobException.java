package com.triakobah.TaskParser.core.handler.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralJobException extends ApiException {
    public GeneralJobException(String exception) {
        super(exception);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
