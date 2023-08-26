package group.oneonetwo.hotelintelligencesystem.modules.orderComment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.po.OrderCommentPO;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.vo.OrderCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 文
* @description 针对表【order_comment】的数据库操作Mapper
* @createDate 2022-05-18 14:19:29
* @Entity group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.OrderComment
*/
@Mapper
public interface OrderCommentMapper extends BaseMapper<OrderCommentPO> {


    String getAvgScore(String hotelId);

    Page<OrderCommentVO> getPage(@Param("page") Page page, @Param("vo") OrderCommentVO vo);
}
