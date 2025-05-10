package com.mail.common.core;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PageResult<T> {
    private int total;  // 总记录数
    private int page;   // 当前页
    private int pageSize; // 每页大小
    private int totalPages; // 总页数
    private T records;  // 当前页的记录数据

    // 构造方法，初始化分页结果
    public PageResult(int total, int page, int pageSize, int totalPages, T records) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.records = records;
        this.totalPages = totalPages;
    }
}
