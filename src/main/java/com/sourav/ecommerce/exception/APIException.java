package com.sourav.ecommerce.exception;

public class APIException extends RuntimeException {
   public static final long serialVersionUID = 1L;
   private String message;
   public APIException(){

   }

   public APIException(String message){
       super(message);
   }
}
