package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class UserAlreadyWithdrewException extends RuntimeException {
    public UserAlreadyWithdrewException() {
        super(ExceptionCode.USER_ALREADY_WITHDREW.getMessage());
    }
}
