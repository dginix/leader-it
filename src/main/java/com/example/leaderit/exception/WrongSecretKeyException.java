package com.example.leaderit.exception;

public class WrongSecretKeyException extends RuntimeException {
    public WrongSecretKeyException(String message) {
        super(message);
    }
}