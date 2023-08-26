package group.oneonetwo.hotelintelligencesystem.modules.discountUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.po.DiscountUserPO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserAndDiscountsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
* @author 文
* @description 针对表【discount_user】的数据库操作Mapper
* @createDate 2022-05-07 16:41:44
* @Entity group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.po.DiscountUser
*/
@Mapper
public interface DiscountUserMapper extends BaseMapper<DiscountUserPO> {


    List<DiscountUserAndDiscountsVO> getMyDiscount(String uid, Date date);
}
