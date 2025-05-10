package com.mail.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mail.admin.entity.SysRoleMenu;
import com.mail.admin.query.SysRoleMenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysRoleMenuMapper 接口
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 查询符合条件的总记录数
     *
     * @param query 查询条件
     * @return 返回符合条件的记录总数
     */
    int findCount(SysRoleMenuQuery query);

    /**
     * 根据分页条件查询数据
     *
     * @param query  查询条件
     * @param offset 偏移量，用于计算数据从哪开始
     * @param limit  每页显示的条数
     * @return 返回当前页的数据列表
     */
    List<SysRoleMenu> findPageList(@Param("query") SysRoleMenuQuery query, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询所有记录
     *
     * @return SysRoleMenu 列表
     */
    List<SysRoleMenu> findAll();

    /**
     * 根据ID查询记录
     *
     * @param id 主键ID
     * @return SysRoleMenu 实体
     */
    SysRoleMenu findById(Long id);

    /**
     * 根据IDS查询数据
     * @param ids 主键ID
     * @return SysRoleMenu 实体对象
     */
    List<SysRoleMenu> findBatchIds(@Param("ids") List<Long> ids);

    /**
     * 插入记录
     *
     * @param entity 实体对象
     * @return 插入的记录数
     */
    int addSysRoleMenu(SysRoleMenu entity);

    /**
     * 根据ID更新记录
     *
     * @param entity 实体对象
     * @return 更新的记录数
     */
    int updateSysRoleMenu(SysRoleMenu entity);

    /**
     * 删除记录
     *
     * @param id 主键ID
     * @return 删除的记录数
     */
    int delSysRoleMenu(Long id);

    /**
     * 根据roleIds查询
     *
     * @param roleIds roleIds
     * @return List<SysRoleMenu>
     */
    List<SysRoleMenu> findByRole(@Param("roleIds") List<Long> roleIds);

}
