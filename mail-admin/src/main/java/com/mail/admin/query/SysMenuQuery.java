package com.mail.admin.query;

import lombok.Getter;
import lombok.Setter;

/**
 * SysMenu 查询对象
 */
@Getter
@Setter
public class SysMenuQuery {

    private int page = 1;       // 当前页码
    private int pageSize = 10;  // 每页记录数
    private Integer id;          // 示例字段，可替换为实际字段

}