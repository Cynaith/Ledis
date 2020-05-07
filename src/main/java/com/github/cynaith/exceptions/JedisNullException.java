package com.github.cynaith.exceptions;


public class JedisNullException extends RuntimeException {
    public JedisNullException(String message){
        super(message);
    }
    public JedisNullException(String message, Throwable cause) {

        super(message, cause);
    }

    public JedisNullException(Throwable cause) {
        super(cause);
    }

}
