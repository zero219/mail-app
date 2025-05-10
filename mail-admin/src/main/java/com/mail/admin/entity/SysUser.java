package com.mail.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键：会员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 密码
     */
    @TableField("pass_word")
    private String passWord;
    /**
     * 别名
     */
    @TableField("user_alias")
    private String userAlias;
    /**
     * 手机
     */
    @TableField("phone")
    private String phone;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 状态（1：正常 0：停用）
     */
    @TableField("is_enabled")
    private Integer isEnabled;
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 删除标记（0:不可用 1:可用）
     */
    @TableField("is_deleted")
    private Integer isDeleted;

}

