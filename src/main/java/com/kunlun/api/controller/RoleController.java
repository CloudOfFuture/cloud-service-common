package com.kunlun.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kunlun.api.service.RoleService;
import com.kunlun.entity.SysRole;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-28.
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;


    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping("/add")
    public DataRet<String> addRole(@RequestBody SysRole sysRole) {
        return roleService.add(sysRole);
    }


    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return
     */
    @PostMapping("/update")
    public DataRet<String> update(@RequestBody SysRole sysRole) {
        return roleService.update(sysRole);
    }


    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public DataRet<String> deleteById(@RequestParam(value = "id") Long id) {
        return roleService.deleteById(id);
    }


    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public DataRet<SysRole> findById(@RequestParam(value = "id") Long id) {
        return roleService.findById(id);
    }


    /**
     * 列表
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    @GetMapping("/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "searchKey", required = false) String searchKey) {
        return roleService.findByCondition(pageNo, pageSize, searchKey);
    }


    /**
     * 角色分配菜单
     *
     * @param object
     * @return
     */
    @PostMapping("/distribution/menu")
    public DataRet<String> menuBindRole(@RequestBody JSONObject object) {
        Long roleId = object.getObject("roleId", Long.class);
        List<Long> menuIdList = object.getJSONArray("menuIdList").toJavaList(Long.class);
        return roleService.menuBindRole(roleId, menuIdList);
    }


    /**
     * 用户分配角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    @PostMapping("/distribution/user")
    public DataRet<String> userBindRole(@RequestParam(value = "roleId") Long roleId,
                                        @RequestParam(value = "userId") Long userId) {
        return roleService.userBindRole(roleId, userId);
    }

    /**
     * 查询卖家角色
     *
     * @return DataRet
     */
    @GetMapping("/distribution/findSellerRole")
    public DataRet<SysRole> findSellerRole() {
        return roleService.findSellerRole();
    }
}
