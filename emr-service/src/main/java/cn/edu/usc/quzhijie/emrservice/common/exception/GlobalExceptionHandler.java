package cn.edu.usc.quzhijie.emrservice.common.exception;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理业务异常
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BizException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.fail(e.getCode(), e.getMessage()));
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(500, "系统内部错误");
    }
}
