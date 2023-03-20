package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message + ExceptionCode.ENTITY_NOT_FOUND.getMessage());
    }
}
