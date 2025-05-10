package com.mail.admin.service;

import com.mail.admin.entity.SysUserRole;
import com.mail.admin.query.SysUserRoleQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mail.common.core.PageResult;

import java.util.List;

/**
 * SysUserRoleService 接口
 * 定义了对 SysUserRole 实体的基本操作，如增、删、改、查等服务方法
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据查询条件进行分页查询
     *
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    PageResult<List<SysUserRole>> findByPage(SysUserRoleQuery query);

    /**
     * 查询所有数据
     *
     * @return SysUserRole 实体列表
     */
    List<SysUserRole> findAll();

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     * @return SysUserRole 实体对象
     */
    SysUserRole findById(Long id);

    /**
     * 根据IDS查询数据
     *
     * @param ids 主键ID
     * @return SysUserRole 实体对象
     */
    List<SysUserRole> findBatchIds(List<Long> ids);

    /**
     * 插入一条数据
     *
     * @param entity SysUserRole 实体对象
     * @return 插入的行数
     */
    int addSysUserRole(SysUserRole entity);

    /**
     * 根据ID更新数据
     *
     * @param entity SysUserRole 实体对象
     * @return 更新的行数
     */
    int updateSysUserRole(SysUserRole entity);

    /**
     * 根据ID删除数据
     *
     * @param id 主键ID
     * @return 删除的行数
     */
    int delSysUserRole(Long id);

    /**
     * 根据用户id查询
     *
     * @param userId userId
     * @return List<SysUserRole>
     */
    List<SysUserRole> findByUser(long userId);
}
