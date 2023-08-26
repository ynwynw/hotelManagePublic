package group.oneonetwo.hotelintelligencesystem.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 文
 * @description 订单数据层
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderPO> {

    public Page<OrderVO> getPage(@Param("page") Page page,@Param("vo") OrderVO vo);

    public List<OrderVO> getAllList(@Param("vo") OrderVO vo);
    public List<OrderVO> getcount(@Param("vo") OrderVO vo);

}
