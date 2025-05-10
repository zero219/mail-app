package com.mail.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_menu")
public class SysMenu implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键：编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属上级
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 菜单标题
     */
    @TableField("title")
    private String title;
    /**
     * 组件名称
     */
    @TableField("component")
    private String component;
    /**
     * 排序
     */
    @TableField("sort_value")
    private Integer sortValue;
    /**
     * 状态(0:禁止,1:正常)
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

