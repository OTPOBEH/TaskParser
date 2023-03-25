package com.triakobah.TaskParser.core.handler;

import com.triakobah.TaskParser.core.constants.ApiConstants;
import com.triakobah.TaskParser.core.handler.exceptions.ErrorMessages;
import com.triakobah.TaskParser.core.handler.exceptions.GeneralJobException;
import com.triakobah.TaskParser.core.handler.exceptions.JobInputException;
import org.slf4j.MDC;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JobExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(JobInputException.class)
    public final ResponseEntity<?> handleJobInputException(JobInputException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGeneralException(Exception ex) {

        GeneralJobException exception = new GeneralJobException(ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE);
        exception.setRequestId(MDC.get(ApiConstants.REQUEST_ID));
        exception.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> validationList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        JobInputException jobInputException = new JobInputException();
        jobInputException.setRequestId(MDC.get(ApiConstants.REQUEST_ID));
        jobInputException.setStatus(HttpStatus.BAD_REQUEST);
        jobInputException.setValidationErrors(validationList);

        return new ResponseEntity<>(jobInputException, HttpStatus.BAD_REQUEST);
    }

}
