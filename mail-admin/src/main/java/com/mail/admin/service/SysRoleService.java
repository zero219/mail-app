package com.mail.admin.service;

import com.mail.admin.entity.SysRole;
import com.mail.admin.query.SysRoleQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mail.common.core.PageResult;

import java.util.List;

/**
 * SysRoleService 接口
 * 定义了对 SysRole 实体的基本操作，如增、删、改、查等服务方法
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据查询条件进行分页查询
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    PageResult<List<SysRole>> findByPage(SysRoleQuery query);

    /**
     * 查询所有数据
     * @return SysRole 实体列表
     */
    List<SysRole> findAll();
    /**
     * 根据ID查询数据
     * @param id 主键ID
     * @return SysRole 实体对象
     */
     SysRole findById(Long id);

    /**
     * 插入一条数据
     * @param entity SysRole 实体对象
     * @return 插入的行数
     */
    int addSysRole(SysRole entity);

    /**
     * 根据ID更新数据
     * @param entity SysRole 实体对象
     * @return 更新的行数
     */
    int updateSysRole(SysRole entity);

    /**
     * 根据ID删除数据
     * @param id 主键ID
     * @return 删除的行数
     */
    int delSysRole(Long id);


}
