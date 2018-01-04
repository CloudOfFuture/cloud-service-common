package com.kunlun.api.service;

import com.kunlun.entity.Ticket;
import com.kunlun.result.DataRet;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
public interface TicketService {

    /**
     * 查询优惠券是否可用
     *
     * @param useTicket
     * @param ticketId
     * @return
     */
    DataRet<String> checkTicket(String useTicket, Long ticketId);

    /**
     * 修改用户优惠券状态
     *
     * @param ticketId
     * @param status
     * @return
     */
    DataRet<String> modifyUserTicketStatus(Long ticketId, String status);

    /**
     * 创建优惠券
     *
     * @param ticket
     * @return
     */
    DataRet add(Ticket ticket);
}
