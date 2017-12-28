package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.api.mapper.CategoryMapper;
import com.kunlun.entity.Category;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@Service
public class CategoryServiceImpl implements CategoryService {


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
        if (categoryId == null || goodId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = categoryMapper.bindCategoryGood(categoryId, goodId);
        if (result > 0) {
            return new DataRet<>("绑定成功");
        }
        return new DataRet<>("ERROR", "绑定失败");
    }

    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    @Override
    public DataRet<String> unbinding(Long goodId) {
        if (goodId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = categoryMapper.unbindGoodId(goodId);
        if (result > 0) {
            return new DataRet<>("解绑成功");
        }
        return new DataRet<>("ERROR", "解绑失败");
    }


    /**
     * 新增类目
     *
     * @param category
     * @return
     */
    @Override
    public DataRet<String> add(Category category) {
        if (category.getCategoryName() == null) {
            return new DataRet<>("ERROR", "请填写类目名称");
        }
        Integer resultName = categoryMapper.validCategoryName(category.getCategoryName());
        if (resultName > 0) {
            return new DataRet<>("ERROR", "名称已存在");
        }
        Integer result = categoryMapper.add(category);
        if (result > 0) {
            return new DataRet<>("新增成功");
        }
        return new DataRet<>("ERROR", "新增失败");
    }


    /**
     * 修改类目
     *
     * @param category
     * @return
     */
    @Override
    public DataRet<String> modify(Category category) {
        if (category.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        int count = categoryMapper.validByNameAndId(category.getId(), category.getCategoryName());
        if (count > 0) {
            return new DataRet<>("ERROR", "已经存在相同名字的类目");
        }
        if (category.getParentId() == category.getId()) {
            return new DataRet<>("ERROR", "修改失败,父id不能为当前记录id");
        }
        //删除操作
        if (CommonEnum.UN_NORMAL.getCode().equals(category.getStatus())) {
            DataRet x = checkStatus(category);
            if (x != null) {
                return x;
            }
        }
        Integer result = categoryMapper.modify(category);
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet<>("ERROR", "修改失败");
    }


    /**
     * 根据id获取类目详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<Category> findById(Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Category category = categoryMapper.findById(id);
        if (category == null) {
            return new DataRet<>("ERROR", "查无结果");
        }
        return new DataRet<>(category);
    }

    /**
     * 根据id删除类目
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<String> deleteById(Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Category category = categoryMapper.findById(id);
        DataRet x = checkStatus(category);
        if (x != null) {
            return x;
        }
        Integer result = categoryMapper.deleteById(id);
        if (result > 0) {
            return new DataRet<>("删除成功");
        }
        return new DataRet<>("ERROR", "删除失败");
    }


    /**
     * 更改类目状态
     *
     * @param status
     * @param id
     * @return
     */
    @Override
    public DataRet<String> updateStatus(String status, Long id) {
        if (id == null || StringUtils.isEmpty(status)) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = categoryMapper.updateStatus(status, id);
        if (result == 0) {
            return new DataRet<>("ERROR", "修改状态失败");
        }
        return new DataRet<>("修改状态成功");
    }

    /**
     * 查询列表
     *
     * @param pageNo
     * @param pageSize
     * @param type
     * @param searchKey
     * @return
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String type, String searchKey) {
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtil.isEmpty(type)) {
            type = null;
        }
        if (StringUtil.isEmpty(searchKey)) {
            searchKey = null;
        }
        if (searchKey != null) {
            searchKey = "%" + searchKey + "%";
        }
        Page<Category> page = categoryMapper.findByCondition(searchKey, type);
        return new PageResult(page);
    }


    /**
     * 商品批量绑定类目
     *
     * @param categoryId
     * @param goodIdList
     * @return
     */
    @Override
    public DataRet<String> bindBatch(Long categoryId, List<Long> goodIdList) {
        if (categoryId == null || goodIdList.size() == 0 || goodIdList == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        goodIdList.forEach(goodId -> {
            categoryMapper.unbindGoodId(goodId);
            categoryMapper.bindCategoryGood(categoryId, goodId);
        });
        return new DataRet<>("绑定成功");
    }


    /**
     * 商品批量解绑
     *
     * @param goodIdList
     * @return
     */
    @Override
    public DataRet<String> unbindBatch(List<Long> goodIdList) {
        if (goodIdList == null || goodIdList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = categoryMapper.unbindCategoryGood(goodIdList);
        if (result == 0) {
            return new DataRet<>("ERROR","解绑失败");
        }
        return new DataRet<>("解绑成功");
    }


    /**
     * 判断当前类目是否再使用中
     *
     * @param category
     * @return
     */
    private DataRet<String> checkStatus(Category category) {
        if (category.getParentId() > 0) {
            //是子类目
            List<Long> idList = new ArrayList<>();
            idList.add(category.getId());
            Integer bindCount = categoryMapper.findCountByCategoryIdList(idList);
            if (bindCount > 0) {
                return new DataRet<>("ERROR", "当前类目正在使用中，请先解绑");
            }
        } else {
            //一级类目
            List<Category> childList = categoryMapper.findByParentId(category.getId());
            List<Long> idList = new ArrayList<>();
            childList.forEach(item -> idList.add(item.getId()));
            Integer bindCount = 0;
            if (idList.size() > 0) {
                bindCount = categoryMapper.findCountByCategoryIdList(idList);
            }
            if (bindCount > 0) {
                return new DataRet<>("ERROR", "当前类目子类目正在使用中，请先解绑");
            }
        }
        return null;
    }
}
