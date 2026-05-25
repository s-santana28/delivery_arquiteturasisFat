package com.deliverytech.delivery_api.exception;



public class ConflictException extends BusinessException {

    private String conflictField;
    private Object conflictValue;

    public ConflictException(String message) {
        super(message,("CONFLICT"));
       
    }

    public ConflictException(String message, String conflictField, Object conflictValue) {
        super(message,("CONFLICT"));
        this.conflictField = conflictField;
        this.conflictValue = conflictValue;
        
    }

    public String getConflictField() {
        return conflictField;
    }

    public Object getConflictValue() {
        return conflictValue;
    }
}