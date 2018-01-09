package com.kunlun.api.hystrix;

import com.kunlun.api.client.OrderClient;
import com.kunlun.entity.Order;
import com.kunlun.result.DataRet;
import com.kunlun.wxentity.UnifiedOrderNotifyRequestData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-09 10:33
 */
@Component
public class OrderClientHystrix implements OrderClient {

    @Override
    public DataRet<List<Order>> findRefundingOrder() {
        return new DataRet<>("ERROR", "请求失败");
    }

    @Override
    public DataRet updateOrderStatusById(Long orderId, String status) {
        return new DataRet<>("ERROR", "请求失败");
    }

    @Override
    public DataRet<List<Order>> findUnPayOrder() {
        return new DataRet<>("ERROR", "请求失败");
    }
}
