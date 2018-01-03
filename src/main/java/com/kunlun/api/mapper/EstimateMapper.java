package com.kunlun.api.mapper;

import com.kunlun.entity.Estimate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-03.
 */
@Mapper
public interface EstimateMapper {

    /**
     * 评价
     *
     * @param estimate
     * @return
     */
    int estimate(@RequestBody Estimate estimate);
}
