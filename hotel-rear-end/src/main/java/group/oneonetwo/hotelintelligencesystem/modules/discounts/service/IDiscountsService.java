package group.oneonetwo.hotelintelligencesystem.modules.discounts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;


import java.util.List;

public interface IDiscountsService {
    DiscountsVO add(DiscountsVO discountsVO);
    DiscountsVO selectOneByIdReturnVO(String id);
    DiscountsPO save(DiscountsVO discountsVO);
    Integer deleteById(String id);
    DiscountsPO selectOneById(String id);
    DiscountsVO saveone (DiscountsVO discountsVO);
    Page<DiscountsVO> getPage(DiscountsVO discountsVO);

    double[] countPay(Integer days, double price, String discount);

    List<DiscountsVO> getHotelDiscountListByHotelId(String id);

    List<DiscountsVO> getPersonalDiscountListByHotelId(String id);

    boolean isLegal(String discountsId);

    List<DiscountsVO> getCurrentCanUseMyPersonalDiscountList(DiscountsVO id);
}

