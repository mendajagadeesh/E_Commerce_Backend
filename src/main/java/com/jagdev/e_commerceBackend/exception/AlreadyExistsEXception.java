package com.jagdev.e_commerceBackend.exception;

public class AlreadyExistsEXception extends RuntimeException {
    String message;
    public AlreadyExistsEXception(String message) {
        super(message);
        this.message = message;
    }
}
