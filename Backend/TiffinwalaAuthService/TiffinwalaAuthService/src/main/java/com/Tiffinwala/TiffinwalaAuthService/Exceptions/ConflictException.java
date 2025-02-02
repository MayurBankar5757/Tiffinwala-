package com.Tiffinwala.TiffinwalaAuthService.Exceptions;

public class ConflictException extends RuntimeException {// for handling duplicate entries
    public ConflictException(String message) {
        super(message);
    }
}
