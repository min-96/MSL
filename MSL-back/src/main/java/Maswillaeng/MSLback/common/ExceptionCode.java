package Maswillaeng.MSLback.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "가 존재하지 않습니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_ALREADY_WITHDREW(HttpStatus.CONFLICT, "탈퇴한 회원입니다."),
    ALREADY_EXIST(HttpStatus.CONFLICT, "가 이미 존재합니다."),
    NOT_EXIST(HttpStatus.BAD_REQUEST, "가 존재하지 않습니다."),
    NOT_AUTHORIZED(HttpStatus.BAD_REQUEST, "에 대한 접근 권한이 없는 사용자입니다."),
    S3_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 파일 저장에 실패했습니다."),

    JWT_TAMPERED(HttpStatus.FORBIDDEN, "변조된 토큰입니다."),
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");


    private final HttpStatus status;
    private final String message;
}
