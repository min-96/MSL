package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException() {
        super(ExceptionCode.PASSWORD_NOT_MATCHED.getMessage());
    }
}
