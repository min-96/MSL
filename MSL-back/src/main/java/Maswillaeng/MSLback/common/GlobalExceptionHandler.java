package Maswillaeng.MSLback.common;

import Maswillaeng.MSLback.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static Maswillaeng.MSLback.common.ExceptionCode.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.info("EntityNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), ENTITY_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> alreadyExistsExceptionHandler(AlreadyExistException e) {
        log.info("AlreadyExistException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), ALREADY_EXIST.getStatus());
    }

    @ExceptionHandler(JwtNotValidException.class)
    public ResponseEntity<Object> jwtNotValidExceptionHandler(JwtNotValidException e) {
        log.info("JwtNotValidException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), JWT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(JwtTamperedException.class)
    public ResponseEntity<Object> jwtTamperedExceptionHandler(JwtTamperedException e) {
        log.info("JwtNotValidException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), JWT_TAMPERED.getStatus());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Object> jwtTamperedExceptionHandler(NotAuthorizedException e) {
        log.info("NotAuthorizedException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), NOT_AUTHORIZED.getStatus());
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<Object> jwtTamperedExceptionHandler(NotExistException e) {
        log.info("NotExistException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), NOT_EXIST.getStatus());
    }

    @ExceptionHandler(PasswordNotMatchedException.class)
    public ResponseEntity<Object> PasswordNotMatchedExceptionHandler(PasswordNotMatchedException e) {
        log.info("PasswordNotMatchedException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), PASSWORD_NOT_MATCHED.getStatus());
    }

    @ExceptionHandler(UserAlreadyWithdrewException.class)
    public ResponseEntity<Object> PasswordNotMatchedExceptionHandler(UserAlreadyWithdrewException e) {
        log.info("UserAlreadyWithdrewException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), USER_ALREADY_WITHDREW.getStatus());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> FileUploadExceptionHandler(FileUploadException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

}
