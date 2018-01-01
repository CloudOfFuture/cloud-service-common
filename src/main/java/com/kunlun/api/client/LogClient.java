package com.kunlun.api.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kunlun.api.hystrix.LogClientHystrix;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 14:18
 */
@FeignClient(value = "cloud-service-log", fallback = LogClientHystrix.class)
public interface LogClient {

    /**
     * 新增商品日志
     *
     * @param jsonArray 日志集合
     * @return DataRet
     */
    @PostMapping("/log/add/goodLogs")
    DataRet saveGoodLogs(@RequestBody JSONArray jsonArray);
}
