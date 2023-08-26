package group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.enums.DiscountEnums;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.mapper.DiscountUserMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.po.DiscountUserPO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserAndDiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.ILogsService;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 文
* @description 针对表【discount_user】的数据库操作Service实现
* @createDate 2022-05-07 16:41:44
*/
@Service
public class DiscountUserServiceImpl implements IDiscountUserService {

    @Autowired
    DiscountUserMapper discountUserMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    ILogsService logsService;

    @Autowired
    IDiscountsService discountsService;

    @Override
    public DiscountUserVO add(DiscountUserVO discountUserVO){
        if(discountUserVO==null){
            throw new SavaException("插入失败:实体为空");
        }


        DiscountUserPO discountUserPO=new DiscountUserPO();
        BeanUtils.copyProperties(discountUserVO,discountUserPO);
        int insert=discountUserMapper.insert(discountUserPO);

        Gson gson = new Gson();
        logsService.createLog("【添加】用户领取优惠券信息",gson.toJson(discountUserVO),1,1);

        if(insert>0){
            return discountUserVO;
        }
        throw new SavaException("插入失败");
    }

    @Override
    public List<DiscountUserAndDiscountsVO> getMyDiscount() {
        return discountUserMapper.getMyDiscount(authUtils.getUid(),new Date());
    }

    @Override
    public void gotCoupon(String discountsId) {
        String uid = authUtils.getUid();
        if (!discountsService.isLegal(discountsId)) {
            throw new CommonException("该优惠卷已过期!");
        }
        if (isGotThisCoupon(uid,discountsId)) {
            throw new CommonException("您已获得过该优惠券!");
        }
        DiscountUserVO discountUserVO = new DiscountUserVO();
        discountUserVO.setUid(uid);
        discountUserVO.setdId(discountsId);
        discountUserVO.setIsUsed(0);
        DiscountUserVO add = add(discountUserVO);
    }

    @Override
    public boolean isGotThisCoupon(String uid, String discountsId) {
        QueryWrapper<DiscountUserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).eq("d_id",discountsId);
        List<DiscountUserPO> discountUserPOS = discountUserMapper.selectList(wrapper);
        if (discountUserPOS.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isUsed(String uid, String discountsId) {
        QueryWrapper<DiscountUserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).eq("d_id",discountsId);
        List<DiscountUserPO> discountUserPOS = discountUserMapper.selectList(wrapper);
        if (discountUserPOS.isEmpty() || discountUserPOS.get(0).getIsUsed() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 更改优惠券状态
     * @param discount
     * @param status 需要更改后的状态
     */
    @Override
    public void changeDiscountStatus(String discount, int status) {
        if (!WStringUtils.isBlank(discount)) {
            String[] split = discount.split(",");
            QueryWrapper<DiscountUserPO> wrapper = new QueryWrapper<>();
            wrapper.eq("uid",authUtils.getUid());
            for (String s : split) {
                wrapper.eq("d_id",s);
                List<DiscountUserPO> po = discountUserMapper.selectList(wrapper);
                if (DiscountEnums.DISCOUNT_USER_UNUSED.getCode().equals(status)) {
                    //返还优惠券
                    if (!po.isEmpty()) {
                        po.get(0).setIsUsed(status);
                    }
                }else {
                    //使用优惠券
                    if (!po.isEmpty()) {
                        po.get(0).setIsUsed(status);
                    }else {
                        throw new CommonException(Integer.valueOf(ResultCode.NO_DATA.getCode()),"优惠券不存在");
                    }
                }
                discountUserMapper.updateById(po.get(0));
            }
        }
    }
}
