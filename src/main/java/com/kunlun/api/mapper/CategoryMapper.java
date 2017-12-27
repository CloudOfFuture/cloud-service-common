package com.kunlun.api.mapper;

import com.kunlun.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@Mapper
public interface CategoryMapper {


    /**
     * 绑商品跟类目
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    Integer bindCategoryGood(@Param("categoryId") Long categoryId,
                             @Param("goodId") Long goodId);



    /**
     * 根据商品id解绑商品跟类目
     *
     * @param goodId
     * @return
     */
    Integer unbindWithGoodId(@Param("goodId") Long goodId);


    /**
     * 校验类目名称是否存在
     *
     * @param categoryName
     * @return
     */
    Integer validCategoryName(@Param("categoryName") String categoryName);



    /**
     * 类目新增
     *
     * @param category
     * @return
     */
    Integer add(Category category);
}
