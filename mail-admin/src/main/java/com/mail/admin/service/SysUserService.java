package com.mail.admin.service;

import com.mail.admin.dto.LoginDto;
import com.mail.admin.entity.SysRole;
import com.mail.admin.entity.SysUser;
import com.mail.admin.query.SysUserQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mail.admin.vo.LoginVo;
import com.mail.admin.vo.MenuDataVo;
import com.mail.admin.vo.RoleVo;
import com.mail.common.core.PageResult;
import com.mail.common.core.Result;

import java.util.List;

/**
 * SysUserService 接口
 * 定义了对 SysUser 实体的基本操作，如增、删、改、查等服务方法
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据查询条件进行分页查询
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    PageResult<List<SysUser>> findByPage(SysUserQuery query);

    /**
     * 查询所有数据
     * @return SysUser 实体列表
     */
    List<SysUser> findAll();
    /**
     * 根据ID查询数据
     * @param id 主键ID
     * @return SysUser 实体对象
     */
     SysUser findById(Long id);

    /**
     * 插入一条数据
     * @param entity SysUser 实体对象
     * @return 插入的行数
     */
    int addSysUser(SysUser entity);

    /**
     * 根据ID更新数据
     * @param entity SysUser 实体对象
     * @return 更新的行数
     */
    int updateSysUser(SysUser entity);

    /**
     * 根据ID删除数据
     * @param id 主键ID
     * @return 删除的行数
     */
    int delSysUser(Long id);

    /**
     * 用户登录
     * @param loginDto  loginDto
     * @return LoginVo
     */
    Result<LoginVo> login(LoginDto loginDto);

    /**
     * 查询用户菜单
     * @return MenuDataVo
     */
    Result<List<MenuDataVo>> menuInfo();

    /**
     *  查询角色信息
     * @return
     */
    Result<List<RoleVo>> findRoleInfo();
}
