package com.kunlun.api.service;

import com.kunlun.result.DataRet;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
public interface TicketService {

    /**
     * 查询优惠券是否可用
     * @param useTicket
     * @param ticketId
     * @return
     */
    String checkTicket(String useTicket, Long ticketId);

    /**
     * 修改用户优惠券状态
     * @param ticketId
     * @param status
     * @return
     */
    DataRet modifyUserTicketStatus(Long ticketId,String status);
}
