package com.kunlun.api.controller;

import com.kunlun.api.service.EstimateService;
import com.kunlun.entity.Estimate;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-03.
 */
@RestController
@RequestMapping("estimate")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    /**
     * 评价
     *
     * @param estimate 评价
     * @return
     */
    @RequestMapping(value = "/estimate")
    public DataRet estimate(@RequestBody Estimate estimate) {
        return estimateService.estimate(estimate);
    }
}
