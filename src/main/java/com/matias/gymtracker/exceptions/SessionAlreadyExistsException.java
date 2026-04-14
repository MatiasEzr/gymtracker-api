package com.matias.gymtracker.exceptions;

public class SessionAlreadyExistsException extends RuntimeException {

    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}