package group.oneonetwo.hotelintelligencesystem.modules.discountUser.service;


import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserAndDiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;

import java.util.List;

/**
* @author 文
* @description 针对表【discount_user】的数据库操作Service
* @createDate 2022-05-07 16:41:44
*/
public interface IDiscountUserService {

    DiscountUserVO add(DiscountUserVO discountUserVO);

    /**
     * 我的未过期未使用的优惠券
     * @return
     */
    List<DiscountUserAndDiscountsVO> getMyDiscount();

    void gotCoupon(String discountsId);

    boolean isGotThisCoupon(String uid, String discountsId);

    boolean isUsed(String uid, String discountsId);

    void changeDiscountStatus(String discount, int status);
}
