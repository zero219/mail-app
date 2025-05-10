package com.mail.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.mail.admin.dto.LoginDto;
import com.mail.admin.entity.*;
import com.mail.admin.mapper.*;
import com.mail.admin.service.SysUserService;
import com.mail.admin.query.SysUserQuery;
import com.mail.admin.util.RedisKeyUtils;
import com.mail.admin.util.ThreadLocalUtils;
import com.mail.admin.vo.LoginVo;
import com.mail.admin.vo.MenuDataVo;
import com.mail.admin.vo.MenuSubDataVo;
import com.mail.admin.vo.RoleVo;
import com.mail.common.core.PageResult;
import com.mail.common.core.Result;
import com.mail.common.enums.ResultCodeEnum;
import com.mail.common.enums.ResultOperation;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.DigestUtils;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * SysUserServiceImpl 类
 * 实现了 SysUserService 接口，提供增、删、改、查等服务
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据查询条件进行分页查询
     *
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    @Override
    public PageResult<List<SysUser>> findByPage(SysUserQuery query) {
        // 计算分页的偏移量，offset = (当前页 - 1) * 每页条数
        int offset = (query.getPage() - 1) * query.getPageSize();

        // 获取每页显示的条数
        int limit = query.getPageSize();

        // 查询符合条件的总记录数
        int total = sysUserMapper.findCount(query);

        // 查询当前页的数据列表，传递查询条件、偏移量和每页条数
        List<SysUser> list = sysUserMapper.findPageList(query, offset, limit);

        // 计算总页数，使用整数除法，避免总页数为零
        int totalPages = (total + query.getPageSize() - 1) / query.getPageSize();

        // 返回分页结果，包含总记录数、当前页、每页条数、总页数以及当前页的数据
        return new PageResult<>(total, query.getPage(), query.getPageSize(), totalPages, list);
    }

    /**
     * 查询所有数据
     *
     * @return SysUser 实体列表
     */
    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     * @return SysUser 实体对象
     */
    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.findById(id);
    }

    /**
     * 插入一条数据
     *
     * @param entity SysUser 实体对象
     * @return 插入的行数
     */
    @Override
    public int addSysUser(SysUser entity) {
        return sysUserMapper.addSysUser(entity);
    }

    /**
     * 根据ID更新数据
     *
     * @param entity SysUser 实体对象
     * @return 更新的行数
     */
    @Override
    public int updateSysUser(SysUser entity) {
        return sysUserMapper.updateSysUser(entity);
    }

    /**
     * 根据ID删除数据
     *
     * @param id 主键ID
     * @return 删除的行数
     */
    @Override
    public int delSysUser(Long id) {
        return sysUserMapper.delSysUser(id);
    }

    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return LoginVo
     */
    @Override
    public Result<LoginVo> login(LoginDto loginDto) {
        Result<LoginVo> result = Result.success(ResultOperation.LOGINSUCCESS.getMsg(), null);
        try {
            String userName = loginDto.getUserName();
            String passWord = DigestUtils.md5DigestAsHex(loginDto.getPassWord().getBytes());
            SysUser userInfo = sysUserMapper.findUserInfo(userName);
            if (userInfo == null) {
                return Result.fail("用户名不正确");
            }
            if (!Objects.equals(userInfo.getPassWord(), passWord)) {
                return Result.fail("密码不正确");
            }
            //登录成功，生成token
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            redisTemplate.opsForValue().set(RedisKeyUtils.USER_LOGIN_KEY + token, JSON.toJSONString(userInfo), 30, TimeUnit.MINUTES);
            LoginVo loginVo = new LoginVo();
            loginVo.setToken(token);
            result.setData(loginVo);
        } catch (Exception ex) {
            result = Result.fail(ResultOperation.LOGINFAIL.getMsg());
        }
        return result;
    }

    /**
     * 查询用户菜单
     *
     * @return MenuDataVo
     */
    @Override
    public Result<List<MenuDataVo>> menuInfo() {
        Result<List<MenuDataVo>> result = new Result<>();
        try {
//            SysUser sysUserInfo = new SysUser();
//            sysUserInfo.setId(1L);

            SysUser sysUserInfo = ThreadLocalUtils.getSysUserInfo();
            // 如果用户不存在
            if (sysUserInfo == null) {
                return Result.fail();
            }

            List<SysUserRole> byUserList = sysUserRoleMapper.findBySysUserRole(sysUserInfo.getId());
            // 获取角色id
            List<Long> roleIds = byUserList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

            // 根据角色id获取菜单
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.findBySysRoleMenu(roleIds);
            List<Long> menuIds = sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

            // 根据菜单id获取菜单信息
            List<SysMenu> sysMenuList = sysMenuMapper.findBatchIds(menuIds);

            List<MenuDataVo> menuDataVoList = new ArrayList<>();
            List<SysMenu> sysMenuParentCollect = sysMenuList.stream().filter(sysMenu -> sysMenu.getParentId() == 0).collect(Collectors.toList());
            for (SysMenu sysMenuParent : sysMenuParentCollect) {
                MenuDataVo menuDataVo = new MenuDataVo();
                menuDataVo.setParentId(sysMenuParent.getParentId());
                menuDataVo.setParentName(sysMenuParent.getTitle());
                menuDataVo.setSort(sysMenuParent.getSortValue());

                List<MenuSubDataVo> menuSubDataVos = new ArrayList<>();
                List<SysMenu> SysMenuCollect = sysMenuList.stream()
                        .filter(sysMenu -> Objects.equals(sysMenu.getParentId(), sysMenuParent.getId())).collect(Collectors.toList());
                for (SysMenu sysMenu : SysMenuCollect) {
                    MenuSubDataVo menuSubDataVo = new MenuSubDataVo();
                    menuSubDataVo.setId(sysMenu.getId());
                    menuSubDataVo.setName(sysMenu.getTitle());
                    menuSubDataVo.setSort(sysMenu.getSortValue());
                    menuSubDataVos.add(menuSubDataVo);
                }

                menuDataVo.setMenuSubDataVos(menuSubDataVos);

                menuDataVoList.add(menuDataVo);
            }
            result = Result.success(menuDataVoList);
        } catch (Exception ex) {
            result = Result.fail(ex.getMessage());
        }
        return result;
    }

    /**
     * 查询角色信息
     *
     * @return Result<List < SysRole>>
     */
    @Override
    public Result<List<RoleVo>> findRoleInfo() {
        Result<List<RoleVo>> result = new Result<>();
        try {
            SysUser sysUserInfo = ThreadLocalUtils.getSysUserInfo();
            // 如果用户不存在
            if (sysUserInfo == null) {
                return Result.fail();
            }

            List<SysUserRole> byUserList = sysUserRoleMapper.findBySysUserRole(sysUserInfo.getId());
            // 获取角色id
            List<Long> roleIds = byUserList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            // 根绝角色id获取角色信息
            List<SysRole> sysRoles = sysRoleMapper.findBatchIds(roleIds);
            List<RoleVo> roleVoList = new ArrayList<>();
            for (SysRole sysRole : sysRoles) {
                RoleVo roleVo = new RoleVo();
                roleVo.setId(sysRole.getId());
                roleVo.setName(sysRole.getRoleName());
                roleVoList.add(roleVo);
            }
            result = Result.success(roleVoList);
        } catch (Exception ex) {
            result = Result.fail(ex.getMessage());
        }
        return result;
    }
}
