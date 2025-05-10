package com.mail.common.enums;

import lombok.Getter;

/**
 * 请求码枚举
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "请求成功"),
    FAIL(500, "请求失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "接口不存在"),
    INVALID_PARAM(400, "请求参数无效"),
    SERVER_ERROR(500, "服务器内部错误"),
    TOO_MANY_REQUESTS(429, "请求过于频繁");

    /**
     * -- GETTER --
     * 获取状态码
     */
    private final int code;
    /**
     * -- GETTER --
     * 获取描述信息
     */
    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}