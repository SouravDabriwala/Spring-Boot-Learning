package com.sourav.ecommerce.exception;

public class isEmptyException extends RuntimeException {
    public static final long serialVersionUID = 1L;
    private String message;
    public isEmptyException(){

    }

    public isEmptyException(String message){
        super(message);
    }
}
