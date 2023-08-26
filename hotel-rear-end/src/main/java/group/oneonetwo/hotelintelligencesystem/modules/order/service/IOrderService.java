package group.oneonetwo.hotelintelligencesystem.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;

import java.util.List;


public interface IOrderService {
    OrderPO selectOneById(String id);
    OrderPO add(OrderVO orderVO);
    OrderVO selectOneByIdReturnVO(String id);
    OrderPO save(OrderVO orderVO);
    Integer deleteById(String id);
    OrderVO saveone(OrderVO orderVO);
    Page<OrderVO> getPage(OrderVO orderVO);
    OrderVO addOne(OrderVO orderVO);
    List<OrderVO> getAllList(OrderVO orderVO);
    void randomOrder();

    OrderVO createNewOrder(OrderVO orderVO);

    String cancelOrder(String id);

    Page<OrderVO> my(OrderVO orderVO);

    List<OrderVO> count(OrderVO orderVO);

    void payOrder(String orderId,String walletPwd);

    //    暂时注释支付宝相关代码
    String prePayOrderForAlipay(String orderId) throws Exception;

    Boolean checkPayOrderForAlipay(String orderId) throws Exception;

    String cancelOrderForAlipay(String orderId) throws Exception;
}
