package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String entityName) {
        super(entityName + ExceptionCode.ALREADY_EXIST.getMessage());
    }
}
