package com.kunlun.api.service;

import com.kunlun.api.mapper.TicketMapper;
import com.kunlun.entity.TicketSnapshot;
import com.kunlun.entity.TicketUser;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketMapper ticketMapper;

    /**
     * 查询优惠券是否可用
     *
     * @param useTicket
     * @param ticketId
     * @return
     */
    @Override
    public DataRet<String> checkTicket(String useTicket, Long ticketId) {
        //判断是否使用优惠券
        if(CommonEnum.UN_USE_TICKET.getCode().equals(useTicket)){
            return new DataRet<>("未使用优惠券");
        }
        //查询用户优惠券详情
        TicketUser ticketUser = ticketMapper.findTicketUserInfo(ticketId);
        if(ticketUser==null){
            return new DataRet<>("ERROR","优惠券信息不存在");
        }
        //根据用户优惠券详情中的快照ID,查找优惠券详情
        TicketSnapshot ticketSnapshot = ticketMapper.findTicketSnapShotInfo(ticketUser.getSnapshotId());
        Logger logger = Logger.getLogger(ticketUser.getStatus());
        logger.info(ticketUser.getStatus());
        if("ALREADY_USED".equals(ticketUser.getStatus())){
            return new DataRet<>("ERROR","优惠券已使用过");
        }else if(ticketSnapshot.getStartDate().after(new Date())){
            return new DataRet<>("ERROR","优惠券还未到使用日期");
        }else if(ticketSnapshot.getEndDate().before(new Date())){
            return new DataRet<>("ERROR","优惠券已过期");
        }
        return new DataRet<>("正常");
    }


    /**
     * 修改用户优惠券状态
     *
     * @param ticketId
     * @param status
     * @return
     */
    @Override
    public DataRet<String> modifyUserTicketStatus(Long ticketId,String status) {
        Integer total = ticketMapper.modifyUserTicketStatus(ticketId,status);
        if(total<=0){
            return new DataRet("ERROE","修改优惠券状态失败");
        }
        return new DataRet("修改成功");
    }
}
