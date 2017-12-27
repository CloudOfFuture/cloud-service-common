package com.kunlun.api.controller;

import com.kunlun.api.service.CategoryService;
import com.kunlun.entity.Category;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    @PostMapping("/unbindCategoryGood")
    public DataRet<String> unbinding(@RequestParam(value = "goodId") Long goodId){
        return categoryService.unbinding(goodId);
    }


    /**
     * 新增类目
     *
     * @param category
     * @return
     */
    @PostMapping("/add/category")
    public DataRet<String> addCategory(@RequestBody Category category){
        return categoryService.add(category);
    }
}
