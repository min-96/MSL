package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class JwtTamperedException extends RuntimeException {
    public JwtTamperedException() {
        super(ExceptionCode.JWT_TAMPERED.getMessage());
    }
}
