package com.kunlun.api.mapper;

import com.kunlun.entity.SendGood;
import com.kunlun.result.DataRet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-09.
 */
@Mapper
public interface SendGoodMapper {

    /**
     * 新增发货信息
     *
     * @param sendGood
     * @return
     */
    Integer add(SendGood sendGood);

    /**
     * 根据id查详情
     *
     * @param id
     * @return
     */
    SendGood findById(@Param("id") Long id);
}
