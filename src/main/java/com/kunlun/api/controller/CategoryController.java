package com.kunlun.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kunlun.api.service.CategoryService;
import com.kunlun.entity.Category;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                @RequestParam(value = "goodId") Long goodId) {
        return categoryService.bind(categoryId, goodId);

    }


    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    @PostMapping("/unbindCategoryGood")
    public DataRet<String> unbinding(@RequestParam(value = "goodId") Long goodId) {
        return categoryService.unbinding(goodId);
    }


    /**
     * 新增类目
     *
     * @param category
     * @return
     */
    @PostMapping("/add/category")
    public DataRet<String> addCategory(@RequestBody Category category) {
        return categoryService.add(category);
    }


    /**
     * 修改类目
     *
     * @param category Category
     * @return
     */
    @PostMapping("/modify")
    public DataRet<String> modify(@RequestBody Category category) {
        return categoryService.modify(category);
    }


    /**
     * 查询详情
     *
     * @param id Long
     * @return
     */
    @GetMapping("/findById")
    public DataRet<Category> findById(@RequestParam(value = "id") Long id) {
        return categoryService.findById(id);
    }


    /**
     * 删除类目
     *
     * @param id Long
     * @return
     */
    @PostMapping("deleteById")
    public DataRet<String> deleteById(Long id) {
        return categoryService.deleteById(id);
    }

    /**
     * 修改类目状态
     * 状态 NORMAL UN_NORMAL
     *
     * @return
     */
    @PostMapping("/updateStatus")
    public DataRet<String> updateStatus(String status, Long id) {
        return categoryService.updateStatus(status, id);
    }

    /**
     * 列表查询
     *
     * @param pageNo    页码
     * @param pageSize  数量
     * @param searchKey 模糊查询信息
     * @param type      PARENT CHILD
     * @return
     */
    @GetMapping("/findByCondition")
    public PageResult findByCondition(@RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "type",required = false) String type,
                                      @RequestParam(value = "searchKey",required = false) String searchKey) {
        return categoryService.findByCondition(pageNo, pageSize, type, searchKey);
    }


    /**
     * 商品批量绑定类目
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/bindBatch")
    public DataRet<String> bindBatch(@RequestBody JSONObject jsonObject){
        Long categoryId=jsonObject.getObject("categoryId",Long.class);
        List<Long> goodIdList=jsonObject.getJSONArray("idList").toJavaList(Long.class);
        return categoryService.bindBatch(categoryId,goodIdList);
    }


    /**
     * 商品批量解绑
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/unbindBatch")
    public DataRet<String> unbindBatch(@RequestBody JSONObject jsonObject){
        List<Long>goodIdList=jsonObject.getJSONArray("idList").toJavaList(Long.class);
        return categoryService.unbindBatch(goodIdList);
    }
}
