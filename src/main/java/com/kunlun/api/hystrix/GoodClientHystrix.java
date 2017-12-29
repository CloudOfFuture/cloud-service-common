package com.kunlun.api.hystrix;

import com.alibaba.fastjson.JSONArray;
import com.kunlun.api.client.GoodClient;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 14:44
 */
@Component
public class GoodClientHystrix implements GoodClient {
    @Override
    public DataRet updateStocks(JSONArray jsonArray) {
        return new DataRet("ERROR", "更新失败");
    }
}
