package cn.edu.usc.quzhijie.userservice.exception;

import cn.edu.usc.quzhijie.userservice.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理业务异常
    @ExceptionHandler(BizException.class)
    public Result<?> handleBusinessException(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(500, "系统内部错误");
    }
}
