package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class JwtNotValidException extends RuntimeException {
    public JwtNotValidException() {
        super(ExceptionCode.JWT_NOT_VALID.getMessage());
    }
}
