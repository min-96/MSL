package Maswillaeng.MSLback.common.exception;

import Maswillaeng.MSLback.common.ExceptionCode;

public class S3FileUploadException extends RuntimeException {
    public S3FileUploadException() {
        super(ExceptionCode.S3_FILE_UPLOAD_FAIL.getMessage());
    }
}
