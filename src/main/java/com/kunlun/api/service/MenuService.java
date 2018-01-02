package com.kunlun.api.service;

import com.kunlun.entity.SysMenu;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:40
 * @desc
 */
public interface MenuService {
    /**
     * 增加
     *
     * @param sysMenu
     * @return
     */
    DataRet add(SysMenu sysMenu);

    /**
     * 条件查询
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey);

    /**
     * 修改
     *
     * @param sysMenu
     * @return
     */
    DataRet update(SysMenu sysMenu);

    /**
     * id查询详情
     *
     * @param id
     * @return
     */
    DataRet findById(Long id);

    /**
     * 删除停用
     *
     * @param id
     * @return
     */
    DataRet deleteById(Long id);

    /**
     * 根据userId 查询列表
     *
     * @param userId
     * @return
     */
    DataRet findByUserId(Long userId);

    /**
     * 根菜单列表
     *
     * @return
     */
    DataRet findRootMenu();


    /**
     * 根据角色id查询菜单列表
     *
     * @param roleId
     * @return
     */
    DataRet findByRoleId(Long roleId);

    /**
     * 查询菜单列表,分层级
     *
     * @return
     */
    DataRet findAllMenu();
}
