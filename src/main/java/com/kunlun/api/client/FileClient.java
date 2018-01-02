package com.kunlun.api.client;

import com.kunlun.api.hystrix.FileClientHystrix;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-02.
 */
@FeignClient(value = "cloud-service-common", fallback = FileClientHystrix.class)
public interface FileClient {

    /**
     * 添加图片
     *
     * @param mallImg 图片对象
     * @return
     */
    @PostMapping(value = "/file/add")
    DataRet<String> add(@RequestBody MallImg mallImg);
}
