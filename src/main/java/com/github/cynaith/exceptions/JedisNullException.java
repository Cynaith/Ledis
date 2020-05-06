package com.github.cynaith.exceptions;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
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
