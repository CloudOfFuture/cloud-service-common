package com.kunlun.api.service;

import com.kunlun.entity.Brand;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-02.
 */
public interface BrandService {

    /**
     * 增加品牌
     *
     * @param brand
     * @return
     */
    DataRet add(Brand brand);

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    DataRet modify(Brand brand);

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    DataRet findBrandById(Integer id);

    /**
     * 分页查询品牌详情/模糊查询
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey);
}
