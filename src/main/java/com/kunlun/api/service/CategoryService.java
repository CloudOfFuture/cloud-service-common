package com.kunlun.api.service;

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
     * @param categoryId
     * @param goodId
     * @return
     */
    DataRet<String> unbinding(Long goodId);
}
