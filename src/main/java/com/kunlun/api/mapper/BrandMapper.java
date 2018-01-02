package com.kunlun.api.mapper;

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
}
