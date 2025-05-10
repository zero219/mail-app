package com.mail.admin.service.impl;

import com.mail.admin.entity.SysMenu;
import com.mail.admin.entity.SysRoleMenu;
import com.mail.admin.mapper.SysMenuMapper;
import com.mail.admin.mapper.SysRoleMenuMapper;
import com.mail.admin.service.SysRoleService;
import com.mail.admin.mapper.SysRoleMapper;
import com.mail.admin.entity.SysRole;
import com.mail.admin.query.SysRoleQuery;
import com.mail.admin.vo.RoleMenuVo;
import com.mail.common.core.PageResult;
import com.mail.common.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SysRoleServiceImpl 类
 * 实现了 SysRoleService 接口，提供增、删、改、查等服务
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据查询条件进行分页查询
     *
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    @Override
    public PageResult<List<SysRole>> findByPage(SysRoleQuery query) {
        // 计算分页的偏移量，offset = (当前页 - 1) * 每页条数
        int offset = (query.getPage() - 1) * query.getPageSize();

        // 获取每页显示的条数
        int limit = query.getPageSize();

        // 查询符合条件的总记录数
        int total = sysRoleMapper.findCount(query);

        // 查询当前页的数据列表，传递查询条件、偏移量和每页条数
        List<SysRole> list = sysRoleMapper.findPageList(query, offset, limit);

        // 计算总页数，使用整数除法，避免总页数为零
        int totalPages = (total + query.getPageSize() - 1) / query.getPageSize();

        // 返回分页结果，包含总记录数、当前页、每页条数、总页数以及当前页的数据
        return new PageResult<>(total, query.getPage(), query.getPageSize(), totalPages, list);
    }

    /**
     * 查询所有数据
     *
     * @return SysRole 实体列表
     */
    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     * @return SysRole 实体对象
     */
    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.findById(id);
    }

    /**
     * 插入一条数据
     *
     * @param entity SysRole 实体对象
     * @return 插入的行数
     */
    @Override
    public int addSysRole(SysRole entity) {
        return sysRoleMapper.addSysRole(entity);
    }

    /**
     * 根据ID更新数据
     *
     * @param entity SysRole 实体对象
     * @return 更新的行数
     */
    @Override
    public int updateSysRole(SysRole entity) {
        return sysRoleMapper.updateSysRole(entity);
    }

    /**
     * 根据ID删除数据
     *
     * @param id 主键ID
     * @return 删除的行数
     */
    @Override
    public int delSysRole(Long id) {
        return sysRoleMapper.delSysRole(id);
    }

    /**
     * 获取菜单信息
     *
     * @param roleId roleId
     * @return Result<List < RoleMenuVo>>
     */
    @Override
    public Result<List<RoleMenuVo>> roleMenuInfo(long roleId) {
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.findByRoleId(roleId);
        List<Long> menuIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        List<SysMenu> sysMenus = sysMenuMapper.findBatchIds(menuIds);
        List<RoleMenuVo> roleMenuVoList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            RoleMenuVo roleMenuVo = new RoleMenuVo();
            roleMenuVo.setId(sysMenu.getId());
            roleMenuVo.setName(sysMenu.getTitle());
            roleMenuVo.setComponent(sysMenu.getComponent());
            roleMenuVo.setSort(sysMenu.getSortValue());
            roleMenuVoList.add(roleMenuVo);
        }
        return Result.success(roleMenuVoList);
    }
}
