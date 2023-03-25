package com.triakobah.TaskParser.core.handler.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException{
    String requestId;

    HttpStatus status;

    public ApiException(String exception) {
        super(exception);
    }

}
