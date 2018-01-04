package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Ticket;
import com.kunlun.entity.TicketSnapshot;
import com.kunlun.entity.TicketUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Mapper
public interface TicketMapper {

    /**
     * 根据 快照id查询优惠券详情
     *
     * @param ticketSnapShotId
     * @return
     */
    TicketSnapshot findTicketSnapShotInfo(@Param("ticketSnapShotId") Long ticketSnapShotId);

    /**
     * 根据tickId查找用户优惠券详情信息
     *
     * @param tickId
     * @return
     */
    TicketUser findTicketUserInfo(@Param("tickId") Long tickId);

    /**
     * 修改用户优惠券状态
     *
     * @param ticketId
     * @return
     */
    int modifyUserTicketStatus(@Param("ticketId") Long ticketId, @Param("status") String status);

    /**
     * 判断优惠券名称是否存在
     *
     * @param ticket
     * @return
     */
    int validByTicketName(Ticket ticket);

    /**
     * 新增优惠券
     *
     * @param ticket
     * @return
     */
    int add(Ticket ticket);

    /**
     * 模糊查询优惠券（带分页）
     *
     * @param searchKey
     * @return
     */
    Page<Ticket> findByCondition(@Param("searchKey") String searchKey);
}
