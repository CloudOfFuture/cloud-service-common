package com.kunlun.api.hystrix;

import com.kunlun.api.client.UserClient;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:43
 * @desc
 */
@Component
public class UserClientHystrix implements UserClient {

    @Override
    public DataRet validAdmin(Long userId) {
        return new DataRet<>("ERROR", "请求失败");
    }
}
