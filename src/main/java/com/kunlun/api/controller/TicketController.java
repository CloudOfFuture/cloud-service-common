package com.kunlun.api.controller;

import com.kunlun.api.service.TicketService;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param useTicket
     * @param ticketId
     * @return
     */
    @GetMapping("checkTicket")
    public String checkTicket(@RequestParam(value = "useTicket") String useTicket,
                              @RequestParam(value = "ticketId") Long ticketId){
        return ticketService.checkTicket(useTicket,ticketId);
    }

    /**
     * 修改用户优惠券状态
     * @param ticketId
     * @param status
     * @return
     */
    @PostMapping("modifyStatus")
    public DataRet modifyStatus(@RequestParam(value = "ticketId") Long ticketId,
                                @RequestParam(value = "status") String status){
        return ticketService.modifyUserTicketStatus(ticketId,status);
    }
}
