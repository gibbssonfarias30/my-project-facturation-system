package com.backfcdev.advancedspringrestapis.exception;

public class ModelNotFoundException extends RuntimeException{
    public ModelNotFoundException(){
        super("Is empty");
    }
    public ModelNotFoundException(String message){
        super(message);
    }
}
