package com.deliverytech.delivery_api.exception;


public class EntityNotFoundException extends BusinessException {

    private String entityName;
    private Object entityId;

    public EntityNotFoundException(String entityName, Object entityId) {
        super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId),("ENTITY_NOT_FOUND"));
        this.entityName = entityName;
        this.entityId = entityId;
        
    }

    public EntityNotFoundException(String message) {
        super(message,("ENTITY_NOT_FOUND"));
        
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}