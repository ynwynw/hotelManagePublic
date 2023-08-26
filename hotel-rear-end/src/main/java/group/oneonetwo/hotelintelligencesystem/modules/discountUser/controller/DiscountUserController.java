package group.oneonetwo.hotelintelligencesystem.modules.discountUser.controller;

import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserAndDiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文
 * @description: 折扣-用户表
 * @date 2022/5/7 17:16
 */
@Api(tags="折扣-用户相关接口")
@RestController
@RequestMapping("api/discountsUser")
public class DiscountUserController {

    @Autowired
    IDiscountUserService discountUserService;

    @ApiOperation("拿到我的优惠券列表")
    @PostMapping("my")
    public Reply<List<DiscountUserAndDiscountsVO>> getListByUid() {
        return Reply.success(discountUserService.getMyDiscount());
    }

    @ApiOperation("领取优惠券")
    @PostMapping("gotCoupon")
    public Reply gotCoupon(String discountsId) {
        discountUserService.gotCoupon(discountsId);
        return Reply.success("领取成功!");
    }


}
