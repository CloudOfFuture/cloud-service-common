package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Ticket;
import com.kunlun.entity.TicketSnapshot;
import com.kunlun.entity.TicketUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Mapper
public interface TicketMapper {

    /**
     * 根据用户优惠券快照id查询优惠券详情
     *
     * @param ticketSnapShotId
     * @return
     */
    TicketSnapshot findTicketSnapShotInfo(@Param("ticketSnapShotId") Long ticketSnapShotId);

    /**
     * 根据ticketId查找用户优惠券详情信息
     *
     * @param ticketId
     * @return
     */
    TicketUser findTicketUserInfo(@Param("ticketId") Long ticketId);

    /**
     * 修改用户优惠券状态
     *
     * @param ticketId
     * @return
     */
    Integer modifyUserTicketStatus(@Param("ticketId") Long ticketId, @Param("status") String status);

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

    /**
     * 校验优惠券名字
     *
     * @param id
     * @param ticketName
     * @return
     */
    Integer validTicketByNameAndId(@Param("id") Long id, @Param("ticketName") String ticketName);

    /**
     * 修改优惠券
     *
     * @param ticket
     * @return
     */
    Integer modifyByTicket(Ticket ticket);

    /**
     * 根据主键id删除优惠券
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("id") Long id);

    /**
     * 根据主键id批量删除优惠券
     *
     * @param list
     * @return
     */
    Integer batchDeleteById(@Param("list") List<Long> list);

    /**
     * 根据主键查询优惠券详情
     *
     * @param id
     * @return
     */
    Ticket findById(@Param("id") Long id);
}
