package com.kunlun.api.client;

import com.kunlun.api.hystrix.OrderClientHystrix;
import com.kunlun.entity.Order;
import com.kunlun.result.DataRet;
import com.kunlun.wxentity.UnifiedOrderNotifyRequestData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-05 14:20
 */
@FeignClient(value = "cloud-service-order", fallback = OrderClientHystrix.class)
public interface OrderClient {
    /**
     * 未退款订单列表
     *
     * @return
     */
    @GetMapping("/wx/order/findRefundingOrder")
    DataRet<List<Order>> findRefundingOrder();

    /**
     * 查询未支付订单
     *
     * @return
     */
    @GetMapping("/wx/order/findUnPayOrder")
    DataRet<List<Order>> findUnPayOrder();

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param status
     * @return
     */
    @PostMapping("/backend/order/updateStatusById")
    DataRet updateOrderStatusById(@RequestParam(value = "id") Long orderId,
                                  @RequestParam(value = "status") String status);


}
