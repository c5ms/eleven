package com.eleven.framework.web;

public class ResourceIdNotValidException extends RuntimeException {

    public ResourceIdNotValidException(String val) {
        super(val+" is not a valid resource id ");
    }

    public ResourceIdNotValidException(String val,Exception cause) {
        super(val+" is not a valid resource id ",cause);
    }



}
