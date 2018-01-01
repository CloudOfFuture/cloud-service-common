package com.kunlun.api.controller;

import com.kunlun.api.service.MenuService;
import com.kunlun.entity.SysMenu;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:41
 * @desc
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 查询菜单根菜单列表
     *
     * @return DataRet
     */
    @GetMapping(value = "/findRootMenu")
    public DataRet findRootMenu() {
        return menuService.findRootMenu();
    }


    /**
     * 分层级查询菜单列表
     *
     * @return DataRet
     */
    @GetMapping(value = "/findAllMenu")
    public DataRet findAllMenu() {
        return menuService.findAllMenu();
    }

    /**
     * 用户菜单列表
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/findByUserId")
    public DataRet findByUserId(@RequestParam(value = "userId") Long userId) {
        return menuService.findByUserId(userId);
    }

    /**
     * 根据角色id查询菜单列表
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/findByRoleId")
    public DataRet findByRoleId(@RequestParam(value = "roleId") Long roleId) {
        return menuService.findByRoleId(roleId);
    }

    /**
     * 菜单列表
     *
     * @param pageNo    Integer
     * @param pageSize  Integer
     * @param searchKey String
     * @return
     */
    @GetMapping(value = "/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "searchKey", required = false) String searchKey) {
        return menuService.findByCondition(pageNo, pageSize, searchKey);
    }

    /**
     * 菜单添加
     *
     * @param sysMenu SysMenu
     * @return DataRet
     */
    @PostMapping(value = "/add")
    public DataRet add(@RequestBody SysMenu sysMenu) {
        return menuService.add(sysMenu);
    }

    /**
     * 菜单修改
     *
     * @param sysMenu SysMenu
     * @return DataRet
     */
    @PostMapping(value = "/update")
    public DataRet update(@RequestBody SysMenu sysMenu) {
        return menuService.update(sysMenu);
    }

    /**
     * 菜单详情
     *
     * @param id Long
     * @return DataRet
     */
    @GetMapping(value = "/findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return menuService.findById(id);
    }

    /**
     * 菜单删除
     *
     * @param id Long
     * @return DataRet
     */
    @PostMapping(value = "/deleteById")
    public DataRet deleteById(@RequestParam(value = "id") Long id) {
        return menuService.deleteById(id);
    }
}
