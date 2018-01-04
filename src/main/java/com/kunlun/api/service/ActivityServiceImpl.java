package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.GoodClient;
import com.kunlun.api.client.LogClient;
import com.kunlun.api.mapper.ActivityMapper;
import com.kunlun.entity.Activity;
import com.kunlun.entity.ActivityGood;
import com.kunlun.entity.Good;
import com.kunlun.entity.GoodLog;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 03:05
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    LogClient logClient;

    @Autowired
    GoodClient goodClient;

    /**
     * 新增
     *
     * @param activity Activity
     * @return DataRet
     */
    @Override
    public DataRet add(Activity activity) {
        if (StringUtils.isEmpty(activity.getActivityType())) {
            return new DataRet("ERROR", "活动类型不能为空");
        }
        if (StringUtils.isEmpty(activity.getName())) {
            return new DataRet("ERROR", "活动名称不能为空");
        }
        int validResult = activityMapper.validName(activity.getName());
        if (validResult > 0) {
            return new DataRet("ERROR", "活动名称不可重复");
        }
        int typeResult = activityMapper.validTypeAndTime(activity.getStartDate(),
                activity.getEndDate(), activity.getActivityType());
        if (typeResult > 0) {
            return new DataRet("ERROR", "相同时间段内，不允许创建相同类型的活动");
        }
        int result = activityMapper.add(activity);
        if (result > 0) {
            return new DataRet<>("创建成功");
        }
        return new DataRet("ERROR", "创建活动失败");
    }

    /**
     * 更新
     *
     * @param activity Activity
     * @return DataRet
     */
    @Override
    public DataRet update(Activity activity) {
        if (activity.getId() == null) {
            return new DataRet("ERROR", "id不能为空");
        }
        int validResult = activityMapper.validNameAndId(activity.getId(), activity.getName());
        if (validResult > 0) {
            return new DataRet("ERROR", "活动名称不可重复");
        }
        int result = activityMapper.update(activity);
        if (result == 0) {
            return new DataRet("ERROR", "修改活动失败");
        }
        return new DataRet<>("修改活动成功");
    }

    /**
     * 删除
     *
     * @param id Long
     * @return DataRet
     */
    @Override
    public DataRet deleteById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "id不能为空");
        }
        int result = activityMapper.deleteById(id);
        if (result > 0) {
            return new DataRet<>("删除活动成功");
        }
        return new DataRet<>("ERROR", "删除活动失败");
    }

    /**
     * @param pageNo    Integer
     * @param pageSize  Integer
     * @param type      SECONDS_KILL 秒杀 SPELL_GROUP 拼团 PERFERRED 优选 FREE试用
     * @param status    NORMAL正常 UN_NORMAL 非正常 EXPIRE已过期
     * @param searchKey String
     * @return PageResult
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String type, String status, String searchKey) {
        if (pageNo == null || pageSize == null) {
            return new PageResult();
        }
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        if (CommonEnum.ALL.getCode().equals(type)) {
            type = null;
        }
        PageHelper.startPage(pageNo, pageSize);
        Page<Activity> page = activityMapper.findByCondition(type, status, searchKey);
        return new PageResult<>(page);
    }

    /**
     * @param id Long
     * @return DataRet
     */
    @Override
    public DataRet findById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "id不能为空");
        }
        Activity activity = activityMapper.findById(id);
        if (activity == null) {
            return new DataRet("ERROR", "查无结果");
        }
        return new DataRet<>(activity);
    }

    /**
     * 修改活动状态
     *
     * @param status 状态  NORMAL正常 UN_NORMAL 非正常 EXPIRE已过期
     * @param idList id集合
     * @return DataRet
     */
    @Override
    public DataRet batchModifyStatus(String status, List<Long> idList) {
        if (StringUtils.isEmpty(status) || idList == null || idList.size() == 0) {
            return new DataRet("ERROR", "参数有误");
        }
        int result = activityMapper.batchModifyStatus(status, idList);
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet("ERROR", "修改失败");
    }

    /**
     * 商品绑定活动
     *
     * @param activityId Long
     * @param goodList   List
     * @return DataRet
     */
    @Override
    public DataRet bindActivityWithGood(Long activityId, List<Good> goodList) {
        if (activityId == null || goodList == null || goodList.size() == 0) {
            return new DataRet("ERROR", "参数有误");
        }
        Activity activity = activityMapper.findById(activityId);
        if (CommonEnum.UN_NORMAL.getCode().equals(activity.getStatus())) {
            return new DataRet("ERROR", "活动已过期");
        }
        if (CommonEnum.EXPIRE.getCode().equals(activity.getStatus())) {
            //已过期
            return new DataRet("ERROR", "活动已过期");
        }
        if (activity.getEndDate().before(new Date())) {
            activity.setStatus(CommonEnum.EXPIRE.getCode());
            activityMapper.update(activity);
            return new DataRet("ERROR", "活动已过期");
        }
        List<GoodLog> goodLogList = new ArrayList<>();
        List<Good> goods = new ArrayList<>();
        //活动跟商品绑定
        goodList.forEach(item -> {
            int result = activityMapper.bindActivityWithGood(activityId,
                    item.getId(),
                    CommonEnum.NORMAL.getCode(),
                    item.getStock());
            if (result > 0) {
                goods.add(CommonUtil.constructGood(item.getId(), -item.getStock()));
                goodLogList.add(CommonUtil.constructGoodLog(item.getId(),
                        "商品绑定活动，商品库存扣减" + item.getStock(),
                        null));
            }
        });
        saveGoodLogs(goodLogList);
        updateGoodStock(goods);
        return new DataRet<>("活动绑定成功");
    }

    /**
     * 商品解绑活动
     *
     * @param activityId Long
     * @param goodIdList List
     * @return DataRet
     */
    @Override
    public DataRet unbindActivityWithGood(Long activityId, List<Long> goodIdList) {
        if (activityId == null || goodIdList == null || goodIdList.size() == 0) {
            return new DataRet("ERROR", "参数有误");
        }
        List<GoodLog> goodLogList = new ArrayList<>();
        List<Good> goodList = new ArrayList<>();
        goodIdList.forEach(goodId -> {
            ActivityGood activityGood = activityMapper.findActivityGoodByGoodId(goodId);
            int result = activityMapper.unbindActivityWithGood(activityId, goodId);
            boolean flag = result > 0 && activityGood != null && activityGood.getStock() > 0;
            if (flag) {
                //返回原有库存
                goodList.add(CommonUtil.constructGood(goodId, activityGood.getStock()));
                GoodLog goodLog = CommonUtil.constructGoodLog(
                        goodId,
                        "解绑活动跟商品关系，商品库存返还" + activityGood.getStock(),
                        null);
                goodLogList.add(goodLog);
            }
        });
        updateGoodStock(goodList);
        saveGoodLogs(goodLogList);
        return new DataRet<>("解绑成功");
    }

    /**
     * 批量保存商品日志
     *
     * @param goodLogList List<GoodLog>
     */
    private void saveGoodLogs(List<GoodLog> goodLogList) {
        if (goodLogList.size() == 0) {
            return;
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(goodLogList);
        logClient.saveGoodLogs(jsonArray);
    }

    /**
     * 批量修改库存
     *
     * @param goodList List<Good>
     */
    private void updateGoodStock(List<Good> goodList) {
        if (goodList.size() == 0) {
            return;
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(goodList);
        goodClient.updateStocks(jsonArray);
    }

    /**
     * 活动列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findByActivityType(Integer pageNo, Integer pageSize, String activityType) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActivityGood> page = activityMapper.findByActivityType(activityType);
        return new PageResult(page);
    }


    /**
     * 校验活动
     *
     * @param goodId
     * @param activityId
     * @param userId
     * @return
     */
    @Override
    public DataRet<String> checkActivity(Long goodId, Long activityId, String userId) {
        //是否参加过活动
        Integer result = activityMapper.validByActivityAndGoodIdAndUserId(goodId, activityId, userId);
        if (result > 0) {
            return new DataRet<>("ERROR", "不能重复参加活动");
        }
        Date currentDate=new Date();
        Activity activity=activityMapper.findById(activityId);
        if (currentDate.after(activity.getEndDate())){
            return new DataRet<>("ERROR","活动已过期");
        }
        if (currentDate.before(activity.getStartDate())){
            return new DataRet<>("ERROR","活动未开始");
        }
        return new DataRet<>("可参加活动");
    }
}
