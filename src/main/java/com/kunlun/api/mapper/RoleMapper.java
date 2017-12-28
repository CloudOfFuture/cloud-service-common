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
    Integer validByNameAndId(@Param("name") String name,@Param("id") Long id);

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

    Long findPidByMenuId(@Param("menuId") Long menuId);

    Integer queryExistByPid(@Param("roleId") Long roleId,@Param("parentId") Long parentId);

    void addRoleMenuRelation(@Param("roleId") Long roleId,@Param("menuId") Long menuId);

    void insertPid(@Param("roleId") Long roleId,@Param("parentId") Long parentId);
}
