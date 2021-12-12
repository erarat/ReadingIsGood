package com.getir.readingisgood.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler
{
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    protected ResponseEntity<RestExceptionResult> handleBusinessException(BusinessException exception, WebRequest request)
    {
        return new ResponseEntity<>(new RestExceptionResult(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<RestExceptionResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request)
    {
        String message = exception.getBindingResult()
                .getFieldErrors().stream()
                .map(p -> p.getField() + "-" + p.getDefaultMessage())
                .collect(Collectors.joining("."));
        return new ResponseEntity<>(new RestExceptionResult(message), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<RestExceptionResult> handleValidationException(ConstraintViolationException exception, WebRequest request)
    {
        return new ResponseEntity<>(new RestExceptionResult(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<RestExceptionResult> handleException(Exception exception, WebRequest request)
    {
        return new ResponseEntity<>(new RestExceptionResult(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
