package com.qk.exception;

/**
 * 自定义异常
 */
public class BusinessException extends RuntimeException{
    public BusinessException() {
    }

    public BusinessException(String message) {  //message异常原因
        super(message);
    }
}