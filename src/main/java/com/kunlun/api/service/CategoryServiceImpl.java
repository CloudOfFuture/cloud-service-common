package com.kunlun.api.service;

import com.kunlun.api.mapper.CategoryMapper;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 商品绑定类目
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    @Override
    public DataRet<String> bind(Long categoryId, Long goodId) {
        if (categoryId==null||goodId==null){
            return new DataRet<>("ERROR","参数错误");
        }
        Integer result=categoryMapper.bindCategoryGood(categoryId,goodId);
        if (result>0){
            return new DataRet<>("绑定成功");
        }
        return new DataRet<>("ERROR","绑定失败");
    }

    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    @Override
    public DataRet<String> unbinding(Long goodId) {
        if (goodId==null){
            return new DataRet<>("ERROR","参数错误");
        }
        Integer result=categoryMapper.unbindWithGoodId(goodId);
        if (result>0){
            return new DataRet<>("解绑成功");
        }
        return new DataRet<>("ERROR","解绑失败");
    }
}
