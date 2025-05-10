package com.mail.common.core;

import com.mail.common.enums.ResultCodeEnum;
import com.mail.common.enums.ResultOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    public Result() {

    }

    public Result(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    /**
     * 成功返回方法（含数据）
     *
     * @param data T
     * @param msg  消息
     * @param <T>  T
     * @return T
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), msg, data, true);
    }

    /**
     * 成功返回方法（含数据）
     *
     * @param data NULL
     * @param <T>  T
     * @return T
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), data, true);
    }

    /**
     * 成功返回方法（无数据）
     *
     * @param <T> T
     * @return T
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), null, true);
    }

    /**
     * 失败返回方法（默认错误信息）
     *
     * @param <T> T
     * @return T
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), ResultOperation.FAIL.getMsg(), null, false);
    }

    /**
     * 失败返回方法（自定义信息）
     *
     * @param message msg
     * @param <T>     T
     * @return T
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), message, null, false);
    }

    /**
     * 失败返回方法（自定义枚举和信息）
     *
     * @param code    code
     * @param message msg
     * @param <T>     T
     * @return <T>
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null, false);
    }
}
