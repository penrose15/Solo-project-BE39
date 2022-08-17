package api.v1.exception;

import lombok.Getter;

public enum ErrorCode {
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_ALREADY_EXIST(409, "Member exists"),

    REGION_CITY_NOT_MATCH(403, "region and city not match");

    @Getter
    private int status;
    @Getter
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
