package com.kunlun.api.controller;

import com.kunlun.api.service.TicketService;
import com.kunlun.entity.Ticket;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@RestController
@RequestMapping("ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    /**
     * 查看优惠券是否可正常使用
     *
     * @param useTicket
     * @param ticketId
     * @return
     */
    @GetMapping("checkTicket")
    public DataRet<String> checkTicket(@RequestParam(value = "useTicket") String useTicket,
                                       @RequestParam(value = "ticketId") Long ticketId) {
        return ticketService.checkTicket(useTicket, ticketId);
    }

    /**
     * 修改用户优惠券状态
     *
     * @param ticketId
     * @param status
     * @return
     */
    @PostMapping("modifyStatus")
    public DataRet<String> modifyStatus(@RequestParam(value = "ticketId") Long ticketId,
                                        @RequestParam(value = "status") String status) {
        return ticketService.modifyUserTicketStatus(ticketId, status);
    }

    /**
     * 创建优惠券
     *
     * @param ticket 优惠券
     * @return
     */
    @PostMapping(value = "/add")
    public DataRet add(@RequestBody Ticket ticket) {
        return ticketService.add(ticket);
    }

    /**
     * 模糊查询优惠券（带分页）
     *
     * @param pageNo    页码
     * @param pageSize  数量
     * @param searchKey 关键字
     * @return
     */
    @GetMapping(value = "/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "searchKey", required = false) String searchKey) {
        return ticketService.findByCondition(pageNo, pageSize, searchKey);
    }

    /**
     * 修改优惠券
     *
     * @param ticket 优惠券
     * @return
     */
    @PostMapping(value = "/modifyByTicket")
    public DataRet modifyByTicket(@RequestBody Ticket ticket) {
        return ticketService.modifyByTicket(ticket);
    }

    /**
     * 根据主键id删除优惠券
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "deleteById")
    public DataRet deleteById(@RequestParam(value = "id") Long id) {
        return ticketService.deleteById(id);
    }

}
