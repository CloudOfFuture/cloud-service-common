package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-28.
 */
@Mapper
public interface RoleMapper {

    /**
     * 校验名称
     *
     * @param name
     * @return
     */
    Integer validName(@Param("name") String name);

    /**
     * 新增
     *
     * @param sysRole
     * @return
     */
    Integer add(SysRole sysRole);


    /**
     * 根据id校验名称
     *
     * @param name
     * @return
     */
    Integer validByNameAndId(@Param("name") String name, @Param("id") Long id);

    /**
     * 校验角色是否与用户绑定
     *
     * @param id
     * @return
     */
    Integer validById(@Param("id") Long id);


    /**
     * 修改信息
     *
     * @param sysRole
     * @return
     */
    Integer update(SysRole sysRole);


    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("id") Long id);


    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    SysRole findById(@Param("id") Long id);


    /**
     * 列表
     *
     * @param searchKey
     * @return
     */
    Page<SysRole> findByCondition(@Param("searchKey") String searchKey);

    /**
     * 清空角色的菜单
     *
     * @param roleId
     * @return
     */
    Integer clearRelation(@Param("roleId") Long roleId);


    /**
     * @param menuId
     * @return
     */
    Long findPidByMenuId(@Param("menuId") Long menuId);


    /**
     * @param roleId
     * @param parentId
     * @return
     */
    Integer queryExistByPid(@Param("roleId") Long roleId, @Param("parentId") Long parentId);

    /**
     * @param roleId
     * @param menuId
     * @return
     */
    Integer addRoleMenuRelation(@Param("roleId") Long roleId, @Param("menuId") Long menuId);


    /**
     * @param roleId
     * @param parentId
     * @return
     */
    Integer insertPid(@Param("roleId") Long roleId, @Param("parentId") Long parentId);

    /**
     * 校验用户是否已经绑定角色
     *
     * @param userId
     * @return
     */
    Integer validUserId(@Param("userId") Long userId);


    /**
     * 更新用户的角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    Integer updateRoleId(@Param("roleId") Long roleId, @Param("userId") Long userId);


    /**
     * 给用户分配角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    Integer addRoleIdAndUserId(@Param("roleId") Long roleId, @Param("userId") Long userId);

    /**
     * 查询卖家角色
     *
     * @return SysRole
     */
    SysRole findSellerRole();
}
