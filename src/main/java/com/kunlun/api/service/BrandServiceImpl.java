package com.kunlun.api.service;

import com.kunlun.api.client.FileClient;
import com.kunlun.api.mapper.BrandMapper;
import com.kunlun.entity.Brand;
import com.kunlun.entity.MallImage;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-02.
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private FileClient fileClient;

    /**
     * 增加品牌
     *
     * @param brand
     * @return
     */
    @Override
    public DataRet add(Brand brand) {
        if (brand.getBrandName() == null) {
            return new DataRet("ERROR", "品牌名字不能为空");
        }
        int count = brandMapper.validByName(brand.getBrandName());
        if (count > 0) {
            return new DataRet("ERROR", "品牌名称重复");
        }
        int result = brandMapper.add(brand);
        if (result > 0) {
            if (StringUtils.isNullOrEmpty(brand.getBrandImage())) {
                MallImg mallImg = new MallImg();
                mallImg.setUrl(brand.getBrandImage());
                mallImg.setTargetId(brand.getId());
                fileClient.add(mallImg);
            }
            return new DataRet("增加品牌成功");
        }
        return new DataRet("ERROR", "增加品牌失败");
    }
}
