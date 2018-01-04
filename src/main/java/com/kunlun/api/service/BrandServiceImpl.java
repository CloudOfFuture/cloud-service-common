package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.FileClient;
import com.kunlun.api.mapper.BrandMapper;
import com.kunlun.entity.Brand;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @Override
    public DataRet modify(Brand brand) {
        if (brand.getId() == null) {
            return new DataRet("ERROR", "未找到品牌");
        }
        Integer result = brandMapper.validByName(brand.getBrandName());
        if (result > 0) {
            return new DataRet("ERROR", "品牌名字不能相同");
        }
        brandMapper.modify(brand);
        return new DataRet("ERROR", "修改品牌成功");
    }

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    @Override
    public DataRet findBrandById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "品牌id不存在");
        }
        Brand brand = brandMapper.findBrandById(id);
        if (brand == null) {
            return new DataRet("ERROR", "未查到品牌信息");
        }
        return new DataRet(brand);
    }

    /**
     * 分页查询品牌详情/模糊查询
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey) {
        if (pageNo == null && pageSize == null) {
            return new PageResult();
        }
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isNullOrEmpty(searchKey)) {
            searchKey = null;
        }
        if (searchKey != null) {
            searchKey = "%" + searchKey + "%";
        }
        Page<Brand> page = brandMapper.findByCondition(searchKey);
        if (page.getTotal() < 0L) {
            return new PageResult(new ArrayList());
        }
        return new PageResult(page);
    }

    /**
     * 批量修改品牌状态
     *
     * @param status 0退出 1入驻 2删除 ENTER 入驻 QUIT退出
     * @param idList id集合
     * @return
     */
    @Override
    public DataRet batchModifyStatus(String status, List<Long> idList) {
        if (idList == null || idList.size() == 0 || StringUtils.isNullOrEmpty(status)) {
            return new DataRet("ERROR", "参数错误");
        }
        brandMapper.batchModifyStatus(status, idList);
        return new DataRet("修改成功");
    }

}
