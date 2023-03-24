package com.triakobah.TaskParser.core.handler;

import com.triakobah.TaskParser.core.handler.exceptions.GeneralJobException;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JobExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(JobInputException.class)
    public final ResponseEntity<?> handleJobInputException(JobInputException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }

    @ExceptionHandler(GeneralJobException.class)
    public final ResponseEntity<?> handleGeneralJobException(GeneralJobException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
    }

}
