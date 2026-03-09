package cn.edu.usc.quzhijie.emrservice.common.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final Integer code;
    public InvalidTokenException(String message) {
        this(401, message);
    }
    public InvalidTokenException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
