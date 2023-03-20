package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String entityName) {
        super(entityName + ExceptionCode.NOT_AUTHORIZED.getMessage());
    }
}
