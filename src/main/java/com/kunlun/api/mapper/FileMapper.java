package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.MallImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午5:28
 * @desc
 */
public interface FileMapper {

    /**
     * 根据图片URL查询
     *
     * @param url String
     * @return int
     */
    MallImg findById(@Param("url") String url);

    /**
     * 根据商品id删除图片
     *
     * @param id Long
     * @return int
     */
    int deleteById(@Param("id") Long id);

    /**
     * 删除图片
     *
     * @param url 图片链接
     */
    void deleteByUrl(@Param("url") String url);

    /**
     * 图片对应的商品ID
     *
     * @param mallImg MallImg
     * @return int
     */
    int modify(MallImg mallImg);

    /**
     * 增加图片
     *
     * @param mallImg MallImg
     */
    void add(MallImg mallImg);

    /**
     * 根据商品ID查找图片列表
     *
     * @param type     类型 商品图片 TYPE_IMG_GOOD,
     *                 品牌图片 TYPE_IMG_BRAND,
     *                 文章图片 TYPE_IMG_ARTICLE,
     *                 商品评价图片 TYPE_IMG_ESTIMATE,
     *                 广告图片 TYPE_IMG_BANNER,
     *                 活动图片 TYPE_IMG_ACTIVITY,
     *                 富文本图片路径 TYPE_IMG_RICH_CONTENT ,
     *                 证件照图片路径  TYPE_IMG_ID_PHOTO
     * @param targetId 目标id
     * @return List
     */
    Page<MallImg> findByCondition(@Param("type") String type,
                                  @Param("targetId") Long targetId);


}
