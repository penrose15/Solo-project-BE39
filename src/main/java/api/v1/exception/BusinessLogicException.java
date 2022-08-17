package api.v1.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException{

    @Getter
    private ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
