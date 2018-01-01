package com.kunlun.api.client;

import com.kunlun.api.hystrix.UserClientHystrix;
import com.kunlun.result.DataRet;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-28上午10:42
 * @desc
 */
@FeignClient(value = "cloud-service-user-center", fallback = UserClientHystrix.class)
public interface UserClient {

    @GetMapping("/user/validAdmin")
    DataRet validAdmin(@RequestParam(value = "userId") Long userId);
}
