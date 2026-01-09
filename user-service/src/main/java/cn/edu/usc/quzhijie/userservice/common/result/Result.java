package cn.edu.usc.quzhijie.userservice.common.result;

import java.io.Serializable;

/**
 * 统一返回结果封装类
 * @param <T> 数据类型
 *
 * 设计规范参考阿里巴巴《Java开发手册》
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

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
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功返回（有数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功返回（自定义提示+数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败返回（默认失败信息）
     */
    public static <T> Result<T> failure() {
        return new Result<>(500, "操作失败", null);
    }

    /**
     * 失败返回（自定义失败信息）
     */
    public static <T> Result<T> failure(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败返回（自定义状态码+提示信息）
     */
    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
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
        return this.code == 200;
    }
}
