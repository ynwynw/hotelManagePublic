package group.oneonetwo.hotelintelligencesystem.modules.discounts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DiscountsMapper extends BaseMapper<DiscountsPO> {
}
