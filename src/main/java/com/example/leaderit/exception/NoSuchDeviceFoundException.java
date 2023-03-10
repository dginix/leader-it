package com.example.leaderit.exception;

public class NoSuchDeviceFoundException extends RuntimeException {
    public NoSuchDeviceFoundException(String message) {
        super(message);
    }
}
