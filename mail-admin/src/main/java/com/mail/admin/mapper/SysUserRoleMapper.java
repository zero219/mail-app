package com.mail.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mail.admin.entity.SysUserRole;
import com.mail.admin.query.SysUserRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysUserRoleMapper 接口
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 查询符合条件的总记录数
     *
     * @param query 查询条件
     * @return 返回符合条件的记录总数
     */
    int findCount(SysUserRoleQuery query);

    /**
     * 根据分页条件查询数据
     *
     * @param query  查询条件
     * @param offset 偏移量，用于计算数据从哪开始
     * @param limit  每页显示的条数
     * @return 返回当前页的数据列表
     */
    List<SysUserRole> findPageList(@Param("query") SysUserRoleQuery query, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询所有记录
     *
     * @return SysUserRole 列表
     */
    List<SysUserRole> findAll();

    /**
     * 根据ID查询记录
     *
     * @param id 主键ID
     * @return SysUserRole 实体
     */
    SysUserRole findById(Long id);

    /**
     * 根据IDS查询数据
     *
     * @param ids 主键ID
     * @return SysUserRole 实体对象
     */
    List<SysUserRole> findBatchIds(@Param("ids") List<Long> ids);

    /**
     * 插入记录
     *
     * @param entity 实体对象
     * @return 插入的记录数
     */
    int addSysUserRole(SysUserRole entity);

    /**
     * 根据ID更新记录
     *
     * @param entity 实体对象
     * @return 更新的记录数
     */
    int updateSysUserRole(SysUserRole entity);

    /**
     * 删除记录
     *
     * @param id 主键ID
     * @return 删除的记录数
     */
    int delSysUserRole(Long id);

    /**
     * 根据用户id查询
     *
     * @param userId userId
     * @return List<SysUserRole>
     */
    List<SysUserRole> findBySysUserRole(long userId);
}
