package com.mail.admin.controller;


import com.mail.admin.service.SysMenuService;
import com.mail.admin.entity.SysMenu;
import com.mail.admin.query.SysMenuQuery;
import com.mail.common.core.PageResult;
import com.mail.common.core.Result;
import com.mail.common.enums.ResultCodeEnum;
import com.mail.common.enums.ResultOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;




/**
 * SysMenuController 类
 * 提供对外的增、删、改、查的接口
 */
@RestController
@RequestMapping("/api/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 分页查询接口
     * 访问路径：/findByPage
     * @param query 查询条件，包含分页信息
     * @return 返回分页结果，包含当前页的数据和分页信息
     */
    @GetMapping("/findByPage")
    public PageResult<List<SysMenu>> findByPage(SysMenuQuery query) {
        return sysMenuService.findByPage(query);
    }

    /**
     * 创建一条数据
     * @param entity SysMenu 实体对象
     * @return 插入的行数
     */
    @PostMapping("/addSysMenu")
    public Result<Integer> addSysMenu(@RequestBody SysMenu entity) {
        Result<Integer> result = new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.FAIL.getMsg(), null, false);
        int res = sysMenuService.addSysMenu(entity);
        if (res > 0) {
            return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), null, true);
        }
        return result;
    }

    /**
     * 更新一条数据
     * @param entity SysMenu 实体对象
     * @return 更新的行数
     */
    @PutMapping("/updateSysMenu")
    public Result<Integer> updateSysMenu(@RequestBody SysMenu entity) {
        Result<Integer> result = new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.FAIL.getMsg(), null, false);
        int res = sysMenuService.updateSysMenu(entity);
        if (res > 0) {
            return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), null, true);
        }
        return result;
    }

    /**
     * 删除一条数据
     * @param id 主键ID
     * @return 删除的行数
     */
    @DeleteMapping("/del/{id}")
    public Result<Integer> delSysMenu(@PathVariable Long id) {
        Result<Integer> result = new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.FAIL.getMsg(), null, false);
        int res = sysMenuService.delSysMenu(id);
        if (res > 0) {
            return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), null, true);
        }
        return result;
    }
}
