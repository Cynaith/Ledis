package com.github.cynaith.exceptions;

/**
 * @USER: lynn
 * @DATE: 2020/5/5
 **/
public class LedisDataException extends RuntimeException{
    public LedisDataException() {
        super();
    }

    public LedisDataException(String message) {
        super(message);
    }
}
