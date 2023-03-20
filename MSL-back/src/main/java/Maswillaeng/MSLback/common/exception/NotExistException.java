package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class NotExistException extends RuntimeException {
    public NotExistException(String entityName) {
        super(entityName + ExceptionCode.NOT_EXIST.getMessage());
    }
}
