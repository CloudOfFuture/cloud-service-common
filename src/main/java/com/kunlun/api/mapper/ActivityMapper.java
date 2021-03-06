package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Activity;
import com.kunlun.entity.ActivityGood;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-29 03:03
 */
@Mapper
public interface ActivityMapper {

    /**
     * 创建活动
     *
     * @param activity
     * @return
     */
    int add(Activity activity);


    /**
     * 修改活动
     *
     * @param activity
     * @return
     */
    int update(Activity activity);

    /**
     * 删除活动
     *
     * @param id 商品ID、主键
     * @return
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件查询活动列表
     *
     * @param type
     * @param status
     * @param searchKey
     * @return
     */
    Page<Activity> findByCondition(@Param("type") String type,
                                   @Param("status") String status,
                                   @Param("searchKey") String searchKey);

    /**
     * 校验活动名称是否存在
     *
     * @param name
     * @return
     */
    int validName(@Param("name") String name);

    /**
     * 根据id 名称校验banner名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    int validNameAndId(@Param("id") Long id,
                       @Param("name") String name);


    /**
     * 根据ID查询活动详情
     *
     * @param id
     * @return
     */
    Activity findById(@Param("id") Long id);

    /**
     * 批量修改活动状态
     *
     * @param status 状态 0正常 1删除
     * @param idList id集合
     * @return int
     */
    int batchModifyStatus(@Param("status") String status,
                          @Param("idList") List<Long> idList);

    /**
     * 校验相同时间内是否存在相同类型的活动
     *
     * @param startDate
     * @param endDate
     * @param activityType
     * @return
     */
    int validTypeAndTime(@Param("startDate") Date startDate,
                         @Param("endDate") Date endDate,
                         @Param("activityType") String activityType);


    /**
     * 添加活动商品匹配关系
     *
     * @param activityId
     * @param goodId
     * @param status
     * @param stock
     * @return
     */
    int bindActivityWithGood(@Param("activityId") Long activityId,
                             @Param("goodId") Long goodId,
                             @Param("status") String status,
                             @Param("stock") Integer stock);


    /**
     * 根据商品id查询关联关系
     *
     * @param goodId
     * @return
     */
    ActivityGood findActivityGoodByGoodId(@Param("goodId") Long goodId);


    /**
     * 解绑商品跟活动
     *
     * @param activityId
     * @param goodId
     * @return
     */
    int unbindActivityWithGood(@Param("activityId") Long activityId,
                               @Param("goodId") Long goodId);

    /**
     * 活动列表
     *
     * @param activityType
     * @return
     */
    Page<ActivityGood> findByActivityType(@Param("activityType") String activityType);


    /**
     * 校验是否参加过活动
     *
     * @param goodId
     * @param activityId
     * @param userId
     * @return
     */
    Integer validByActivityAndGoodIdAndUserId(@Param("goodId") Long goodId,
                                              @Param("activityId") Long activityId,
                                              @Param("userId") String userId);

    /**
     * 获取活动商品信息
     *
     * @param goodId
     * @return
     */
    ActivityGood findByActivityIdAndGoodId(@Param("goodId") Long goodId);


    /**
     * 库存扣减
     *
     * @param id
     * @param count
     * @return
     */
    Integer updateStock(@Param("id") Long id, @Param("count") int count);
}
