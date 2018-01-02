package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-02.
 */
@Mapper
public interface BrandMapper {

    /**
     * 判断品牌名字是否存在
     *
     * @param brandName
     * @return
     */
    int validByName(@Param("brandName") String brandName);

    /**
     * 增加品牌
     *
     * @param brand
     * @return
     */
    int add(Brand brand);

    /**
     * 根据id查找品牌详情
     *
     * @param id
     * @return
     */
    Brand findBrandById(@Param("id") Integer id);

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    int modify(Brand brand);

    /**
     * 分页查询品牌详情/模糊查询
     *
     * @param searchKey
     * @return
     */
    Page<Brand> findByCondition(@Param("searchKey") String searchKey);
}
