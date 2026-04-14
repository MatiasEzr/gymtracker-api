package com.matias.gymtracker.exceptions;

public class ExistEmailException extends RuntimeException {
    public ExistEmailException(String email) {

        super("The email " + email + " already exists");
    }

}
