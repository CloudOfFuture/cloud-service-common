package com.kunlun.api.client;

import com.alibaba.fastjson.JSONArray;
import com.kunlun.api.hystrix.GoodClientHystrix;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 14:43
 */
@FeignClient(value = "cloud-service-good", fallback = GoodClientHystrix.class)
public interface GoodClient {

    /**
     * 批量修改库存
     *
     * @param jsonArray JSONArray
     * @return DataRet
     */
    @PostMapping("/backstage/good/updateStocks")
    DataRet updateStocks(@RequestBody JSONArray jsonArray);
}
