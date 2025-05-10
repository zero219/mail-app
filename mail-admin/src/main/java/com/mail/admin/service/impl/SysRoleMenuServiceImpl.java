package com.mail.admin.service.impl;

import com.mail.admin.service.SysRoleMenuService;
import com.mail.admin.mapper.SysRoleMenuMapper;
import com.mail.admin.entity.SysRoleMenu;
import com.mail.admin.query.SysRoleMenuQuery;
import com.mail.common.core.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * SysRoleMenuServiceImpl 类
 * 实现了 SysRoleMenuService 接口，提供增、删、改、查等服务
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
    * 根据查询条件进行分页查询
    * @param query 查询条件，包含分页信息
    * @return 返回分页结果，包含当前页的数据和分页信息
    */
    @Override
    public PageResult<List<SysRoleMenu>> findByPage(SysRoleMenuQuery query) {
        // 计算分页的偏移量，offset = (当前页 - 1) * 每页条数
        int offset = (query.getPage() - 1) * query.getPageSize();

        // 获取每页显示的条数
        int limit = query.getPageSize();

        // 查询符合条件的总记录数
        int total = sysRoleMenuMapper.findCount(query);

        // 查询当前页的数据列表，传递查询条件、偏移量和每页条数
        List<SysRoleMenu> list = sysRoleMenuMapper.findPageList(query, offset, limit);

        // 计算总页数，使用整数除法，避免总页数为零
        int totalPages = (total + query.getPageSize() - 1) / query.getPageSize();

        // 返回分页结果，包含总记录数、当前页、每页条数、总页数以及当前页的数据
        return new PageResult<>(total, query.getPage(), query.getPageSize(), totalPages, list);
    }

    /**
     * 查询所有数据
     * @return SysRoleMenu 实体列表
     */
    @Override
    public List<SysRoleMenu> findAll() {
        return sysRoleMenuMapper.findAll();
    }

    /**
     * 根据ID查询数据
     * @param id 主键ID
     * @return SysRoleMenu 实体对象
     */
    @Override
    public SysRoleMenu findById(Long id) {
        return sysRoleMenuMapper.findById(id);
    }

    /**
     * 根据IDS查询数据
     * @param ids 主键ID
     * @return SysRoleMenu 实体对象
     */
    @Override
    public List<SysRoleMenu> findBatchIds(List<Long> ids){
        return sysRoleMenuMapper.findBatchIds(ids);
    }

    /**
     * 插入一条数据
     * @param entity SysRoleMenu 实体对象
     * @return 插入的行数
     */
    @Override
    public int addSysRoleMenu(SysRoleMenu entity) {
        return sysRoleMenuMapper.addSysRoleMenu(entity);
    }

    /**
     * 根据ID更新数据
     * @param entity SysRoleMenu 实体对象
     * @return 更新的行数
     */
    @Override
    public int updateSysRoleMenu(SysRoleMenu entity) {
        return sysRoleMenuMapper.updateSysRoleMenu(entity);
    }

    /**
     * 根据ID删除数据
     * @param id 主键ID
     * @return 删除的行数
     */
    @Override
    public int delSysRoleMenu(Long id) {
        return sysRoleMenuMapper.delSysRoleMenu(id);
    }

}
