package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:40
 * @desc
 */
@Mapper
public interface MenuMapper {

    /**
     * 新增
     *
     * @param sysMenu SysMenu
     * @return Integer
     */
    Integer add(SysMenu sysMenu);

    /**
     * 条件查询
     *
     * @param searchKey String
     * @return Page
     */
    Page<SysMenu> findByCondition(@Param("searchKey") String searchKey);

    /**
     * 修改
     *
     * @param sysMenu  SysMenu
     * @return Integer
     */
    Integer update(SysMenu sysMenu);


    /**
     * 根据id查询详情
     *
     * @param id Long
     * @return SysMenu
     */
    SysMenu findById(@Param("id") Long id);

    /**
     * 停用/删除
     *
     * @param id Long
     * @return Integer
     */
    Integer deleteById(@Param("id") Long id);

    /**
     * 名称-url校验
     *
     * @param menuName String
     * @param url  String
     * @return Integer
     */
    Integer validByNameAndUrl(@Param("menuName") String menuName,
                              @Param("url") String url);

    /**
     * 名称-id-url校验-id
     *
     * @param id   Long
     * @param menuName String
     * @param url  String
     * @return Integer
     */
    Integer validByIdAndNameAndUrl(@Param("id") Long id,
                                   @Param("menuName") String menuName,
                                   @Param("url") String url);


    /**
     * 查询根节点
     *
     * @return List
     */
    List<SysMenu> findRootMenuList();


    /**
     * 根据userid-parentId查询列表
     *
     * @param parentId Long
     * @param userId   Long
     * @return List
     */
    List<SysMenu> findByParentIdAndUserId(@Param("parentId") Long parentId,
                                          @Param("userId") Long userId);

    /**
     * 根据角色id查询菜单列表
     *
     * @param roleId   Long
     * @param parentId Long
     * @param type     type   一级菜单 PARENT_MENU  二级菜单 SUBMENU
     * @return List
     */
    List<SysMenu> findMenuListByRoleId(@Param("roleId") Long roleId,
                                       @Param("parentId") Long parentId,
                                       @Param("type") Integer type);

    /**
     * 菜单删除校验
     * @param id Long
     * @return Integer
     */
    Integer validDelete(@Param("id") Long id);
}
