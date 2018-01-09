package com.kunlun.api.service;

import com.kunlun.entity.SendGood;
import com.kunlun.result.DataRet;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-09.
 */
public interface SendGoodService {

    /**
     * 新增发货信息
     *
     * @param sendGood
     * @return
     */
    DataRet add(SendGood sendGood);

    /**
     * 根据id查详情
     *
     * @param id
     * @return
     */
    DataRet findById(Long id);
}
