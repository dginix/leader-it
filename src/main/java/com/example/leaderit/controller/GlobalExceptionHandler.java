package com.example.leaderit.controller;

import com.example.leaderit.dto.ErrorMessage;
import com.example.leaderit.exception.NoSuchDeviceFoundException;
import com.example.leaderit.exception.WrongSecretKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDeviceFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNoSuchDeviceFoundException(NoSuchDeviceFoundException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), exception.getMessage());
    }

    @ExceptionHandler(WrongSecretKeyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleWrongSecretKeyException(WrongSecretKeyException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), exception.getMessage());
    }
}
