package com.kunlun.api.service;

import com.kunlun.api.mapper.SendGoodMapper;
import com.kunlun.entity.SendGood;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-09.
 */
@Service
public class SendGoodServiceImpl implements SendGoodService {

    @Autowired
    private SendGoodMapper sendGoodMapper;

    /**
     * 新增发货信息
     *
     * @param sendGood
     * @return
     */
    @Override
    public DataRet add(SendGood sendGood) {
        if (sendGood.getOrderId() == null) {
            return new DataRet("ERROR", "参数错误，id不存在");
        }
        Integer result = sendGoodMapper.add(sendGood);
        if (result > 0) {
            return new DataRet("ERROR", "新增发货信息成功");
        }
        return new DataRet("ERROR", "新增发货信息失败");
    }

    /**
     * 根据id查详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet findById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "参数错误，id不存在");
        }
        SendGood result = sendGoodMapper.findById(id);
        if (result == null) {
            return new DataRet("ERROR", "未查到订单详情");
        }
        return new DataRet(result);
    }
}
