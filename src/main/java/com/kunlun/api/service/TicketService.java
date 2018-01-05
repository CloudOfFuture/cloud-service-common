package com.kunlun.api.service;

import com.kunlun.entity.Ticket;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.util.List;

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

    /**
     * 模糊查询优惠券（带分页)
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey);

    /**
     * 修改优惠券
     *
     * @param ticket
     * @return
     */
    DataRet modifyByTicket(Ticket ticket);

    /**
     * 根据主键id删除优惠券
     *
     * @param id
     * @return
     */
    DataRet deleteById(Long id);

    /**
     * 根据主键id批量删除优惠券
     *
     * @param list
     * @return
     */
    DataRet batchDeleteById(List<Long> list);
}
