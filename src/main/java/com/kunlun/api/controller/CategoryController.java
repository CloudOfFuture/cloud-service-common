package com.kunlun.api.controller;

import com.kunlun.api.service.CategoryService;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 商品绑定类目
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    @PostMapping("/bindCategoryGood")
    public DataRet<String> bind(@RequestParam(value = "categoryId") Long categoryId,
                                @RequestParam(value = "goodId") Long goodId){
        return categoryService.bind(categoryId,goodId);
    }


    @PostMapping("/unbindCategoryGood")
    public DataRet<String> unbinding(@RequestParam(value = "goodId") Long goodId){
        return categoryService.unbinding(goodId);
    }
}
