package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.UserClient;
import com.kunlun.api.mapper.MenuMapper;
import com.kunlun.entity.SysMenu;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:40
 * @desc
 */
@Service
public class MenuServieImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserClient userClient;

    @Override
    public DataRet add(SysMenu sysMenu) {
        Integer validCount = menuMapper.validByNameAndUrl(sysMenu.getMenuName(), sysMenu.getUrl());
        if (validCount > 0) {
            return new DataRet("ERROR", "菜单名称或地址已存在");
        }
        menuMapper.add(sysMenu);
        return new DataRet<>("添加成功");
    }

    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey) {
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        PageHelper.startPage(pageNo, pageSize);
        Page<SysMenu> page = menuMapper.findByCondition(searchKey);
        return new PageResult<>(page);
    }

    @Override
    public DataRet update(SysMenu sysMenu) {
        if (sysMenu.getId() == null) {
            return new DataRet("ERROR", "参数错误");
        }
        Integer validCount = menuMapper.validByIdAndNameAndUrl(
                sysMenu.getId(), sysMenu.getMenuName(), sysMenu.getUrl());
        if (validCount > 0) {
            return new DataRet("ERROR", "菜单名称或地址已存在");
        }
        Integer result = menuMapper.update(sysMenu);
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet("ERROR", "修改失败");
    }

    @Override
    public DataRet findById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "参数错误");
        }
        SysMenu menu = menuMapper.findById(id);
        if (menu == null) {
            return new DataRet("ERROR", "查无结果");
        }
       return new DataRet<>(menu);
    }

    @Override
    public DataRet deleteById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "参数错误");
        }
        Integer result=menuMapper.validDelete(id);
        if(result>0){
            return new DataRet("ERROR", "删除失败,菜单正在使用中");
        }
        Integer delResult = menuMapper.deleteById(id);
        if (delResult==0) {
            return new DataRet("ERROR", "删除失败");
        }
        return new DataRet<>("删除成功");
    }



    @Override
    public DataRet findByUserId(Long userId) {
        if (userId == null) {
            return new DataRet("ERROR", "参数错误");
        }
        //TODO:
        DataRet validResult = userClient.validAdmin(userId);
        List<SysMenu> list;
        if (validResult.isSuccess()) {
            //超级管理员用户菜单
            list = menuMapper.findMenuListByRoleId(null, null, 0);
            list.forEach(menu -> {
                List<SysMenu> childMenuList = menuMapper.findMenuListByRoleId(null, menu.getId(), 1);
                menu.setLeaf(childMenuList);
            });
        } else {
            list = menuMapper.findByParentIdAndUserId(null, userId);
            list.forEach(menu -> {
                List<SysMenu> childMenuList = menuMapper.findByParentIdAndUserId(menu.getId(), userId);
                menu.setLeaf(childMenuList);
            });
        }
        return new DataRet<>(list);
    }

    @Override
    public DataRet findRootMenu() {
        List<SysMenu> data = menuMapper.findRootMenuList();
        if(data.size()==0){
            return new DataRet<>("ERROR","查无结果");
        }
        return new DataRet<>(data);
    }

    @Override
    public DataRet findByRoleId(Long roleId) {
        List<SysMenu> list = menuMapper.findMenuListByRoleId(roleId, null, 0);
        list.forEach(item -> {
            //二级菜单
            List<SysMenu> childMenus = menuMapper.findMenuListByRoleId(roleId, item.getId(), 1);
            item.setLeaf(childMenus);
        });
        if(list.size()==0){
            return new DataRet<>("ERROR","查无结果");
        }
        return new DataRet<>(list);
    }

    @Override
    public DataRet findAllMenu() {
        return findByRoleId(null);
    }
}
