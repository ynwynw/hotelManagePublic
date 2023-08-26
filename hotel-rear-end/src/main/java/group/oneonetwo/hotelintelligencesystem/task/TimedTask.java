package group.oneonetwo.hotelintelligencesystem.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 文
 * @description 定时任务管理
 */
public class TimedTask {

    @Autowired
    IOrderService orderService;


}
