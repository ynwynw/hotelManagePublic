package group.oneonetwo.hotelintelligencesystem.modules.orderComment.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.vo.OrderCommentVO;

/**
* @author 文
* @description 针对表【order_comment】的数据库操作Service
* @createDate 2022-05-18 14:19:29
*/
public interface IOrderCommentService{

    OrderCommentVO add(OrderCommentVO orderCommentVO);

    void writeComment(OrderCommentVO orderCommentVO);

    String getAvgScore(String hotelId);

    Page<OrderCommentVO> getPage(OrderCommentVO orderCommentVO);
}
