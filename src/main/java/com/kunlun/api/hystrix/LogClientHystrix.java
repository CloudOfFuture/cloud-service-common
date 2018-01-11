package com.kunlun.api.hystrix;

import com.alibaba.fastjson.JSONArray;
import com.kunlun.api.client.LogClient;
import com.kunlun.entity.OrderLog;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 14:19
 */
@Component
public class LogClientHystrix implements LogClient {
    @Override
    public DataRet saveGoodLogs(JSONArray jsonArray) {
        return new DataRet<>("ERROR", "新增请求失败");
    }

    @Override
    public DataRet<String> addOrderLog(OrderLog orderLog) {
        return new DataRet<>("ERROR", "新增请求失败");
    }

}
