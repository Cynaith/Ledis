package com.github.cynaith.exceptions;


public class LedisDataException extends RuntimeException{
    public LedisDataException() {
        super();
    }

    public LedisDataException(String message) {
        super(message);
    }
}
