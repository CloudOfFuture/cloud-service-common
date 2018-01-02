package com.kunlun.api.hystrix;

import com.kunlun.api.client.FileClient;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-02.
 */
@Component
public class FileClientHystrix implements FileClient {

    /**
     * 添加图片
     *
     * @param mallImg 图片对象
     * @return
     */
    @Override
    public DataRet<String> add(MallImg mallImg) {
        return new DataRet<>("ERROR", "增加图片失败");
    }
}
