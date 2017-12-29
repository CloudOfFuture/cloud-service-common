package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.api.mapper.RoleMapper;
import com.kunlun.entity.SysRole;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-28.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @Override
    public DataRet<String> add(SysRole sysRole) {
        if (sysRole == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer validName = roleMapper.validName(sysRole.getName());
        if (validName > 0) {
            return new DataRet<>("ERROR", "名称已存在");
        }
        Integer result = roleMapper.add(sysRole);
        if (result == 0) {
            return new DataRet<>("ERROR", "创建失败");
        }
        return new DataRet<>("创建成果");
    }


    /**
     * 修改信息
     *
     * @param sysRole
     * @return
     */
    @Override
    public DataRet<String> update(SysRole sysRole) {
        if (sysRole.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer validByNameAndId = roleMapper.validByNameAndId(sysRole.getName(), sysRole.getId());
        if (validByNameAndId > 0) {
            return new DataRet<>("ERROR", "名称已存在");
        }
        //校验用户是否与角色绑定
        if (CommonEnum.UN_NORMAL.getCode().equals(sysRole.getStatus())) {
            Integer validById = roleMapper.validById(sysRole.getId());
            if (validById > 0) {
                return new DataRet<>("角色正在使用,不可删除");
            }
        }
        Integer result = roleMapper.update(sysRole);
        if (result == 0) {
            return new DataRet<>("ERROR", "修改角色信息失败");
        }
        return new DataRet<>("修改角色信息成功");
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<String> deleteById(Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer validById = roleMapper.validById(id);
        if (validById > 0) {
            return new DataRet<>("角色正在使用,不可删除");
        }
        Integer result = roleMapper.deleteById(id);
        if (result == 0) {
            return new DataRet<>("ERROR", "删除失败");
        }
        return new DataRet<>("删除成功");
    }


    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<SysRole> findById(Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        SysRole sysRole = roleMapper.findById(id);
        if (sysRole == null) {
            return new DataRet<>("ERROR", "查无结果");
        }
        return new DataRet<>(sysRole);
    }


    /**
     * 列表
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtil.isEmpty(searchKey)) {
            searchKey = null;
        }
        if (StringUtil.isNotEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        Page<SysRole> page = roleMapper.findByCondition(searchKey);
        return new PageResult(page);
    }

    /**
     * 角色分配菜单
     *
     * @param roleId
     * @param menuIdList
     * @return
     */
    @Override
    public DataRet<String> getMenu(Long roleId, List<Long> menuIdList) {
        if (roleId == null || menuIdList == null || menuIdList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer clearResult = roleMapper.clearRelation(roleId);
        if (clearResult == 0) {
            return new DataRet<>("ERROR", "清空失败");
        }
        menuIdList.forEach(menuId -> {
            //查询父节点
            Long parentId = roleMapper.findPidByMenuId(menuId);
            Integer count = roleMapper.queryExistByPid(roleId, parentId);
            if (count > 0) {
                roleMapper.addRoleMenuRelation(roleId, menuId);
            } else {
                roleMapper.insertPid(roleId, parentId);
                roleMapper.addRoleMenuRelation(roleId, menuId);
            }
        });
        return new DataRet<>("分配成功");
    }


    /**
     * 用户分配角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    @Override
    public DataRet<String> getUser(Long roleId, Long userId) {
        if (roleId == null || userId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer validResult = roleMapper.validUserId(userId);
        if (validResult > 0) {
            Integer updateResult=roleMapper.updateRoleId(roleId,userId);
            if (updateResult>0){
                return new DataRet<>("分配成功");
            }
        }
        Integer result=roleMapper.addRoleIdAndUserId(roleId,userId);
        return null;
    }
}
