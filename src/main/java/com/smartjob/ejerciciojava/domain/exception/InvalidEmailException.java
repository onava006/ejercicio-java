package com.smartjob.ejerciciojava.domain.exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String invalidEmailError) {
        super(invalidEmailError);
    }
}
