package com.kunlun.api.controller;

import com.kunlun.api.service.SendGoodService;
import com.kunlun.entity.SendGood;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-09.
 */
@RestController
@RequestMapping("/sendGood")
public class SendGoodController {

    @Autowired
    private SendGoodService sendGoodService;

    /**
     * 新增发货信息
     *
     * @param sendGood 发货
     * @return
     */
    @PostMapping(value = "/add")
    public DataRet add(@RequestBody SendGood sendGood) {
        return sendGoodService.add(sendGood);
    }

    /**
     * 根据id查详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return sendGoodService.findById(id);
    }
}



