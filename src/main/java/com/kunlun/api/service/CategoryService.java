package com.kunlun.api.service;

import com.kunlun.entity.Category;
import com.kunlun.result.DataRet;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
public interface CategoryService {

    /**
     * 商品绑定类目
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    DataRet<String> bind(Long categoryId, Long goodId);

    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    DataRet<String> unbinding(Long goodId);

    /**
     * 新增类目
     *
     * @param category
     * @return
     */
    DataRet<String> add(Category category);


    /**
     * 修改类目
     *
     * @param category
     * @return
     */
    DataRet<String> modify(Category category);

    /**
     * 根据id获取类目详情
     *
     * @param id
     * @return
     */
    DataRet<Category> findById(Long id);

    DataRet<String> deleteById(Long id);
}
