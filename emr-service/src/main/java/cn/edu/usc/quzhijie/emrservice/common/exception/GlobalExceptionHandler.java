package cn.edu.usc.quzhijie.emrservice.common.exception;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import io.lettuce.core.RedisConnectionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理业务异常
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BizException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.fail(e.getCode(), e.getMessage()));
    }

    // DTO参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();

        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.fail(400, message));
    }

    // 普通参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> handleConstraintViolationException(ConstraintViolationException e) {

        String message = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("参数校验失败");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.fail(400, message));
    }

    // 处理缺失请求参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<?>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.fail(400, "缺少请求参数: " + e.getParameterName()));
    }

    // 处理失效Token异常
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Result<?>> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Result.fail(401, "无效Token"));
    }

    // 处理资源访问错误异常
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Result<?>> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Result.fail(404, "无效资源"));
    }

    // 处理连接异常
    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<Result<?>> handleRedisConnectionFailureException(RedisConnectionFailureException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.fail(500, "Redis连接异常"));
    }

    // 权限不足
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Result<?>> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Result.fail(403, "权限不足"));
    }

    // 违反数据库唯一索引
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Result<?>> handleDuplicateKeyException(
            DuplicateKeyException e
    ) {

        String msg = e.getMessage();

        if (msg != null && msg.contains("uk_schedule_seq")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Result.fail(400, "已存在重复号源"));
        }
        if (msg != null && msg.contains("uk_doctor_date")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Result.fail(400, "该医生排班信息有重复"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Result.fail(400, "数据重复"));
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception e) {
        e.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.fail(500, "系统内部错误"));
    }
}
