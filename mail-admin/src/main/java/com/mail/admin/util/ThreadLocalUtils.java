package com.mail.admin.util;

import com.mail.admin.entity.SysUser;

public class ThreadLocalUtils {

    private static final ThreadLocal<SysUser> threadLocalSysUser = new ThreadLocal<>();

    // 定义存储数据的静态方法
    public static void setSysUserInfo(SysUser sysUser) {
        threadLocalSysUser.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser getSysUserInfo() {
        return threadLocalSysUser.get();
    }

    // 删除数据的方法
    public static void removeSysUserInfo() {
        threadLocalSysUser.remove();
    }

}
