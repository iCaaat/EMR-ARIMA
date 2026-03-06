package cn.edu.usc.quzhijie.emrservice.common.result;

import java.io.Serializable;

/**
 * 统一返回结果封装类
 * @param <T> 数据类型
 *
 * 设计规范参考阿里巴巴《Java开发手册》
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = 200;
    /** 请求参数错误 */
    public static final int BAD_REQUEST = 400;
    /** 未认证（未登录） */
    public static final int UNAUTHORIZED = 401;
    /** 无权限访问 */
    public static final int FORBIDDEN = 403;
    /** 资源不存在 */
    public static final int NOT_FOUND = 404;
    /** 请求方法不支持 */
    public static final int METHOD_NOT_ALLOWED = 405;
    /** 服务器内部错误 */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /** 用户不存在 */
    public static final int USER_NOT_EXIST = 4001;
    /** 用户名或密码错误 */
    public static final int USERNAME_OR_PASSWORD_ERROR = 4002;
    /** 账号已被禁用 */
    public static final int ACCOUNT_DISABLED = 4003;
    /** 参数校验失败 */
    public static final int VALIDATION_ERROR = 4004;
    /** 数据已存在（如注册重复） */
    public static final int DATA_ALREADY_EXISTS = 4005;
    /** 数据不存在 */
    public static final int DATA_NOT_EXIST = 4006;
    /** 系统异常 */
    public static final int SYSTEM_ERROR = 5000;
    /** 数据库操作失败 */
    public static final int DATABASE_ERROR = 5001;
    /** 第三方服务调用失败 */
    public static final int THIRD_PARTY_SERVICE_ERROR = 5002;

    /** 状态码：200表示成功，其他表示失败 */
    private int code;

    /** 提示信息 */
    private String message;

    /** 返回数据 */
    private T data;

    // ================== 构造方法 ==================

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ================== 静态方法 ==================

    /**
     * 成功返回（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS, "操作成功", null);
    }

    /**
     * 成功返回（有数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, "操作成功", data);
    }

    /**
     * 成功返回（自定义提示+数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS, message, data);
    }

    /**
     * 失败返回（默认失败信息）
     */
    public static <T> Result<T> fail() {
        return new Result<>(INTERNAL_SERVER_ERROR, "操作失败", null);
    }

    /**
     * 失败返回（自定义失败信息）
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(INTERNAL_SERVER_ERROR, message, null);
    }

    /**
     * 失败返回（自定义状态码+提示信息）
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    /** 失败返回（自定义状态码+提示信息+数据） */
    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    // ================== Getter / Setter ==================

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // ================== 便捷方法 ==================

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return this.code == SUCCESS;
    }
}
