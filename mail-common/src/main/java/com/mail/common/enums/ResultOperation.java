package com.mail.common.enums;

import lombok.Getter;

@Getter
public enum ResultOperation {
    FAIL(0, "操作失败"),
    SUCCESS(1, "操作成功"),
    LOGINSUCCESS(2, "登录成功"),
    LOGINFAIL(3, "登录失败")
    ;


    private final int code;
    private final String msg;

    ResultOperation(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
