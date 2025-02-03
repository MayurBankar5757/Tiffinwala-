package com.Tiffinwala.TiffinwalaCrudService.Exceptions;

public class ConflictException extends RuntimeException {// for handling duplicate entries
    public ConflictException(String message) {
        super(message);
    }
}
