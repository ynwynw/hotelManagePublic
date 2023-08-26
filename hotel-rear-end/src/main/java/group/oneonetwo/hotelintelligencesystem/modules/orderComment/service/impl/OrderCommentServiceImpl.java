package group.oneonetwo.hotelintelligencesystem.modules.orderComment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.enums.OrderEnums;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.dao.OrderCommentMapper;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.po.OrderCommentPO;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.vo.OrderCommentVO;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.service.IOrderCommentService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 文
* @description 针对表【order_comment】的数据库操作Service实现
* @createDate 2022-05-18 14:19:29
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrderCommentServiceImpl implements IOrderCommentService {

    @Autowired
    OrderCommentMapper orderCommentMapper;

    @Autowired
    LogsService logsService;

    @Autowired
    IOrderService orderService;

    @Override
    public OrderCommentVO add(OrderCommentVO orderCommentVO){
        if(orderCommentVO==null){
            throw new SavaException("插入评论失败:评论实体为空");
        }


        OrderCommentPO orderCommentPO=new OrderCommentPO();
        BeanUtils.copyProperties(orderCommentVO,orderCommentPO);
        int insert=orderCommentMapper.insert(orderCommentPO);

        Gson gson = new Gson();
//        orderCommentVO.setOtherPolicy(WStringUtils.removeHtml(orderCommentVO.getOtherPolicy()));
        logsService.createLog("【添加】评论信息",gson.toJson(orderCommentVO),1,1);

        if(insert>0){
            BeanUtils.copyProperties(orderCommentPO,orderCommentVO);
            return orderCommentVO;
        }
        throw new SavaException("插入评论失败");
    }

    @Override
    public void writeComment(OrderCommentVO orderCommentVO) {
        //检查订单
        OrderPO orderPO = orderService.selectOneById(orderCommentVO.getOrderId());
        if (orderPO == null) {
            throw new CommonException("该订单不存在");
        }
        if (!OrderEnums.STATUS_DONE.getCode().toString().equals(orderPO.getStatus())) {
            throw new CommonException("该订单未完成,不能评论");
        }
        orderCommentVO.setHotelId(orderPO.getHotelId());
        OrderCommentVO add = add(orderCommentVO);

        OrderVO update = new OrderVO();
        update.setId(orderPO.getId());
        update.setCommentId(add.getId());
        orderService.save(update);
    }

    @Override
    public String getAvgScore(String hotelId) {
        return orderCommentMapper.getAvgScore(hotelId);
    }

    @Override
    public Page<OrderCommentVO> getPage(OrderCommentVO orderCommentVO) {
        Page<Object> page = new Page<>(orderCommentVO.getPage().getPage(), orderCommentVO.getPage().getSize());

        return orderCommentMapper.getPage(page,orderCommentVO);
    }
}
