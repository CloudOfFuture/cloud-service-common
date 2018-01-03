package com.kunlun.api.service;

import com.kunlun.entity.Estimate;
import com.kunlun.result.DataRet;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-03.
 */
public interface EstimateService {

    /**
     * 评价
     *
     * @param estimate
     * @return
     */
    DataRet estimate(Estimate estimate);
}
