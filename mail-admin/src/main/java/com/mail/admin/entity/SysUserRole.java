package com.mail.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键：主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
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

