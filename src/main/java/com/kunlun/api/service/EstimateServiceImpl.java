package com.kunlun.api.service;

import com.kunlun.api.mapper.EstimateMapper;
import com.kunlun.entity.Estimate;
import com.kunlun.result.DataRet;
import com.kunlun.utils.WxUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-03.
 */
@Service
public class EstimateServiceImpl implements EstimateService {

    @Autowired
    private EstimateMapper estimateMapper;

    /**
     * 评价
     *
     * @param estimate
     * @return
     */
    @Override
    public DataRet estimate(Estimate estimate) {
        String userId = WxUtil.getOpenId(estimate.getWxCode());
        if (StringUtils.isNullOrEmpty(userId)) {
            return new DataRet("ERROR", "参数错误");
        }
        Long goodId = estimate.getGoodId();
        if (goodId == null) {
            return new DataRet("ERROR", "参数错误");
        }
        int result = estimateMapper.estimate(estimate);
        if (result > 0) {
            return new DataRet("评价成功");
        }
        return new DataRet("ERROR", "评价失败");
    }
}
