package com.mail.admin.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo {
    /**
     * token
     */
    public String token;
    /**
     * 刷新token
     */
    public String assToken;
}
