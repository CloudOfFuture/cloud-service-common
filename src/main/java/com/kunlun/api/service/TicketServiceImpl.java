package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.TicketMapper;
import com.kunlun.constant.Constant;
import com.kunlun.entity.Ticket;
import com.kunlun.entity.TicketSnapshot;
import com.kunlun.entity.TicketUser;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Service
public class TicketServiceImpl implements TicketService {

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
        if (CommonEnum.UN_USE_TICKET.getCode().equals(useTicket)) {
            return new DataRet<>("未使用优惠券");
        }
        //查询用户优惠券详情
        TicketUser ticketUser = ticketMapper.findTicketUserInfo(ticketId);
        if (ticketUser == null) {
            return new DataRet<>("ERROR", "优惠券信息不存在");
        }
        //根据用户优惠券详情中的快照ID,查找优惠券详情
        TicketSnapshot ticketSnapshot = ticketMapper.findTicketSnapShotInfo(ticketUser.getSnapshotId());
        if (ticketSnapshot == null) {
            return new DataRet<>("ERROR", "用户优惠券详情不存在");
        }
        Logger logger = Logger.getLogger(ticketUser.getStatus());
        logger.info(ticketUser.getStatus());
        if ("ALREADY_USED".equals(ticketUser.getStatus())) {
            return new DataRet<>("ERROR", "优惠券已使用过");
        } else if (ticketSnapshot.getStartDate().after(new Date())) {
            return new DataRet<>("ERROR", "优惠券还未到使用日期");
        } else if (ticketSnapshot.getEndDate().before(new Date())) {
            return new DataRet<>("ERROR", "优惠券已过期");
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
    public DataRet<String> modifyUserTicketStatus(Long ticketId, String status) {
        Integer total = ticketMapper.modifyUserTicketStatus(ticketId, status);
        if (total <= 0) {
            return new DataRet<>("ERROR", "修改优惠券状态失败");
        }
        return new DataRet<>("修改成功");
    }

    /**
     * 新增优惠券
     *
     * @param ticket
     * @return
     */
    @Override
    public DataRet add(Ticket ticket) {
        if (ticket.getMoney() < ticket.getReduceMoney()) {
            return new DataRet("ERROR", "满减金额有误");
        }
        int validByTicketName = ticketMapper.validByTicketName(ticket);
        if (validByTicketName > 0) {
            return new DataRet("ERROR", "优惠券名字已经存在");
        }
        if (ticket.getStartDate().after(new Date()) || ticket.getEndDate().before(new Date())) {
            return new DataRet("ERROR", "新创建的优惠券时间不对");
        }
        if (ticket.getNum() == 0) {
            return new DataRet("ERROR", "新创建的优惠券数量必须大于0");
        }
        int result = ticketMapper.add(ticket);
        if (result < 0) {
            return new DataRet("ERROR", "创建优惠券失败");
        }
        return new DataRet("创建优惠券成功");
    }

    /**
     * 模糊查询优惠券（带分页）
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNo, pageSize);
        if (pageNo == null || pageSize == null) {
            return new PageResult("ERROR", "传入的分页参数有误");
        }
        if (!StringUtils.isNullOrEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        Page<Ticket> page = ticketMapper.findByCondition(searchKey);
        page.forEach(ticket -> {
            if (ticket.getEndDate() != null && ticket.getEndDate().before(new Date())) {
                ticket.setStatus(CommonEnum.EXPIRE.getCode());
            }
            if (ticket.getStartDate() != null && ticket.getStartDate().after(new Date())) {
                ticket.setStatus(CommonEnum.UN_START.getCode());
            }
        });
        return new PageResult(page);
    }

    /**
     * 修改优惠券
     *
     * @param ticket
     * @return
     */
    @Override
    public DataRet modifyByTicket(Ticket ticket) {
        if (ticket.getId() == null) {
            return new DataRet("ERROR", "未拿到优惠券id");
        }
        Integer validTicketByNameAndId = ticketMapper.validTicketByNameAndId(ticket.getId(), ticket.getTicketName());
        if (!validTicketByNameAndId.equals(0)) {
            return new DataRet("ERROR", "优惠券名字已存在");
        }
        Integer result = ticketMapper.modifyByTicket(ticket);
        if (result < 0) {
            return new DataRet("ERROR", "修改优惠券失败");
        }
//        if (ticket.getNum() <= 0) {
//            return new DataRet("EORRR", "修改出错，优惠券数量不能为0");
//        }
        return new DataRet("修改优惠券成功");
    }

    /**
     * 根据主键id删除优惠券
     *
     * @param id
     * @return
     */
    @Override
    public DataRet deleteById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "参数错误，id不存在");
        }
        Integer result = ticketMapper.deleteById(id);
        if (result > 0) {
            return new DataRet("删除优惠券成功");
        }
        return new DataRet("ERROR", "删除优惠券失败");
    }

    /**
     * 根据主键id批量删除优惠券
     *
     * @param list
     * @return
     */
    @Override
    public DataRet batchDeleteById(List<Long> list) {
        if (list == null || list.size() == 0) {
            return new DataRet("ERROR", "参数错误，id不存在");
        }
        Integer result = ticketMapper.batchDeleteById(list);
        if (result > 0) {
            return new DataRet("批量删除优惠券成功");
        }
        return new DataRet("ERROR", "批量删除优惠失败");
    }

    /**
     * 根据主键查询优惠券详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet findById(Long id) {
        if (id == null) {
            return new DataRet("ERROR", "参数错误，id不存在");
        }
        Ticket ticket = ticketMapper.findById(id);
        if (ticket == null) {
            return new DataRet("ERROR", "优惠券没找到");
        }
        if (ticket.getEndDate().before(new Date())) {
            ticket.setStatus(CommonEnum.EXPIRE.getCode());
            ticketMapper.modifyByTicket(ticket);
        }
        return new DataRet(ticket);
    }
}
