package group.oneonetwo.hotelintelligencesystem.modules.discounts.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.enums.DiscountEnums;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;

import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserAndDiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.dao.DiscountsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DiscountsServiceImpl implements IDiscountsService {
    @Autowired
    DiscountsMapper discountsMapper;

    @Autowired
    LogsService logsService;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IDiscountUserService discountUserService;

    @Override
    public DiscountsVO add(DiscountsVO discountsVO) {
        if (discountsVO == null) {
            throw new SavaException(("插入用户失败,折扣实体为空"));
        }
        DiscountsPO discountsPO = new DiscountsPO();
        BeanUtils.copyProperties(discountsVO, discountsPO);
        discountsPO.setHotelId(authUtils.getUserHotelId());
        int insert = discountsMapper.insert(discountsPO);
        Gson gson = new Gson();
        logsService.createLog("【添加】折扣信息", gson.toJson(discountsVO), 1, 1);
        if (insert > 0) {
            return discountsVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public DiscountsVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501, "参数为空");
        }
        DiscountsPO discountsPO = discountsMapper.selectById(id);
        DiscountsVO discountsVO = new DiscountsVO();
        if (discountsPO != null) {
            BeanUtils.copyProperties(discountsPO, discountsVO);
        }
        return discountsVO;
    }

    @Override
    public DiscountsPO save(DiscountsVO discountsVO) {
        if (discountsVO == null) {
            throw new CommonException(501, "折扣实体为空");
        }
        DiscountsVO check = selectOneByIdReturnVO(discountsVO.getId());
        if (check == null) {
            throw new CommonException(4004, "找不到id为'" + discountsVO.getId() + "'的数据");
        }
        DiscountsPO discountsPO = new DiscountsPO();
        BeanUtils.copyProperties(discountsVO, discountsPO);
        int save = discountsMapper.updateById(discountsPO);

        if (save > 0) {
            return discountsMapper.selectById(discountsPO.getId());
        }
        throw new SavaException("折扣修改失败");
    }

    @Override
    public Integer deleteById(String id) {
        DiscountsVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004, "找不到id为'" + id + "'的数据");
        }
        Gson gson = new Gson();
        logsService.createLog("【删除】折扣信息", gson.toJson(check), 1, 1);
        int i = discountsMapper.deleteById(id);
        return i;
    }

    @Override
    public DiscountsPO selectOneById(String id) {
        DiscountsPO discountsPO = discountsMapper.selectById(id);
        return discountsPO;
    }

    @Override
    public DiscountsVO saveone(DiscountsVO discountsVO) {
        DiscountsVO before = selectOneByIdReturnVO(discountsVO.getId());
        DiscountsPO save = save(discountsVO);
        BeanUtils.copyProperties(save, discountsVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】折扣信息", gson.toJson(before) + "@*@" + gson.toJson(save), 1, 1);
        return discountsVO;
    }

    @Override
    public Page<DiscountsVO> getPage(DiscountsVO discountsVO) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        if (!"admin".equals(authUtils.getRole())) {
            wrapper.eq("hotel_id", authUtils.getUserHotelId());
        }

        Page<DiscountsPO> page = new Page<>(discountsVO.getPage().getPage(), discountsVO.getPage().getSize());
        Page<DiscountsPO> poiPage = (Page<DiscountsPO>) discountsMapper.selectPage(page, wrapper);
        return ConvertUtils.transferPage(poiPage, DiscountsVO.class);
    }

    /**
     * 计算价格
     *
     * @param days     天数
     * @param price    房间价格
     * @param discount 折扣
     * @return int[0]为原价, int[1]为折后价
     */
    @Override
    public double[] countPay(Integer days, double price, String discount) {
        double[] pays = new double[2];
        pays[0] = days * price;
        //检查使用的优惠券是否合法
        discountGroupIsIllegal(discount,pays[0],days);
        //下面可写优惠政策
        if (WStringUtils.isBlank(discount)) {
            pays[1] = pays[0];
        }else {
            double reduceFee = 0;
            List<String> discountIds = Arrays.stream(discount.split(",")).collect(Collectors.toList());
            QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<DiscountsPO>();
            wrapper.in("id",discountIds);
            List<DiscountsPO> discountsPOS = discountsMapper.selectList(wrapper);
            Iterator<DiscountsPO> iterator = discountsPOS.iterator();
            while(iterator.hasNext()) {
                DiscountsPO item = iterator.next();
                if (DiscountEnums.DISCOUNTS_TYPE_DISCOUNT.getCode().equals(item.getDiscountsType())) {
                    reduceFee += price*(1-item.getDiscounts());
                }else if (DiscountEnums.DISCOUNTS_TYPE_REDUCTION.getCode().equals(item.getDiscountsType())) {
                    reduceFee += item.getDiscounts();
                }
            }
            //计算价格,并保留两位小数
            pays[1] = Math.round((pays[0] - reduceFee)*100)*0.01;
        }
        //标记优惠券使用
        discountUserService.changeDiscountStatus(discount,DiscountEnums.DISCOUNT_USER_USED.getCode());
        return pays;
    }

    @Override
    public List<DiscountsVO> getHotelDiscountListByHotelId(String id) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.eq("hotel_id", id).ge("validity_time", new Date()).eq("type", DiscountEnums.TYPE_HOTEL.getCode());
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);
        return res;
    }

    @Override
    public List<DiscountsVO> getPersonalDiscountListByHotelId(String id) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.eq("hotel_id", id).ge("validity_time", new Date()).eq("type", DiscountEnums.TYPE_HOTEL.getCode());
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);
        String uid = authUtils.getUid();
        Iterator<DiscountsVO> iterator = res.iterator();
        while (iterator.hasNext()) {
            DiscountsVO next = iterator.next();
            next.setIsGot(discountUserService.isGotThisCoupon(uid, next.getId()));
        }

        return res;
    }

    @Override
    public boolean isLegal(String discountsId) {
        DiscountsPO discountsPO = selectOneById(discountsId);
        if (discountsPO == null) {
            return false;
        }
        if (new Date().after(discountsPO.getValidityTime())) {
            return false;
        }
        return true;
    }

    @Override
    public List<DiscountsVO> getCurrentCanUseMyPersonalDiscountList(DiscountsVO discountsVO) {
        if (WStringUtils.isBlank(discountsVO.getHotelId())) {
            throw new CommonException("酒店ID参数为空");
        }
        if (WStringUtils.isBlank(discountsVO.getBookDay())) {
            throw new CommonException("预订天数参数为空");
        }
        if (WStringUtils.isBlank(discountsVO.getOrderFee())) {
            throw new CommonException("订单价格参数为空");
        }
        //查出已有全部可用优惠券(全局优惠券和当前酒店优惠券)
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.ge("validity_time", new Date())
                .and(w1 -> w1.eq("type", DiscountEnums.TYPE_ALL.getCode())
                        .or(w2 -> w2.eq("type", DiscountEnums.TYPE_HOTEL.getCode()).eq("hotel_id", discountsVO.getHotelId()))
                );
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);

        //查询互斥卷
        String exclusiveAllDiscount = null;
        Set<String> exclusiveSet = new HashSet<>();
        if (!WStringUtils.isBlank(discountsVO.getCurrentUse())) {
            String[] split = discountsVO.getCurrentUse().split(",");
            for (String i : split) {
                DiscountsPO discountsPO = selectOneById(i);
                if (DiscountEnums.EXCLUSIVE_TYPE_ALL.getCode().equals(discountsPO.getExclusiveType())) {
                    exclusiveAllDiscount = discountsPO.getId();
                    break;
                } else if (DiscountEnums.EXCLUSIVE_TYPE_NONE.getCode().equals(discountsPO.getExclusiveType())) {
                    continue;
                }else if (DiscountEnums.EXCLUSIVE_TYPE_RANGE.getCode().equals(discountsPO.getExclusiveType())) {
                    String[] exclusiveArr = discountsPO.getExclusiveRange().split(",");
                    for (String e: exclusiveArr) {
                        exclusiveSet.add(e);
                    }
                }
            }
        }


        Iterator<DiscountsVO> iterator = res.iterator();
        String uid = authUtils.getUid();
        final String HAVE_EXCLUSIVE = "当前使用的券中与该券互斥";
        final String CONDITION_NOT_MATCH = "当前订单不符合该券使用条件";
        while (iterator.hasNext()) {
            DiscountsVO next = iterator.next();
            //把使用过的和没领取的卷去除
            if (discountUserService.isUsed(uid, next.getId())) {
                iterator.remove();
                continue;
            }

            //标记可使用的卷
            //有唯一券的情况下
            if (!WStringUtils.isBlank(exclusiveAllDiscount)) {
                if (exclusiveAllDiscount.equals(next.getId())) {
                    next.setCanUse(true);
                }else {
                    next.setCanUse(false);
                    next.setCantUseReason(HAVE_EXCLUSIVE);
                }
            //有互斥券
            }else if (!exclusiveSet.isEmpty()) {
                if (exclusiveSet.contains(next.getId())) {
                    next.setCanUse(false);
                    next.setCantUseReason(HAVE_EXCLUSIVE);
                }else {
                    next.setCanUse(true);
                }
            }else {
                next.setCanUse(true);
            }

            //验证非无门槛券
            if (!DiscountEnums.EFFECT_TYPE_ALL.getCode().equals(next.getEffectType())) {
                if (DiscountEnums.EFFECT_TYPE_DAYS.getCode().equals(next.getEffectType()) && Integer.parseInt(discountsVO.getBookDay()) >= next.getEffectCondition()) {
                    next.setCanUse(true);
                } else if (DiscountEnums.EFFECT_TYPE_MONEY.getCode().equals(next.getEffectType()) && Double.parseDouble(discountsVO.getOrderFee()) >= next.getEffectCondition()) {
                    next.setCanUse(true);
                } else {
                    next.setCanUse(false);
                    next.setCantUseReason(CONDITION_NOT_MATCH);
                }
            }
        }

        return res;
    }

    /**
     * 优惠券组是否非法(检测是否拥有未使用的、是否过期、是否互斥、是否满足满减条件)
     * @param discount
     * @return 合法：false
     */
    private boolean discountGroupIsIllegal(String discount,double price,Integer days) {
        //不用券时百分百合法
        if (WStringUtils.isBlank(discount)) {
            return false;
        }
        String[] split = discount.split(",");
        Set<String> discounts = Arrays.stream(split).collect(Collectors.toSet());
        Set<String> myDiscounts = discountUserService.getMyDiscount().stream()
                .map(DiscountUserAndDiscountsVO::getdId).collect(Collectors.toSet());
        if (myDiscounts.isEmpty()) {
            throw new CommonException("没有可用的优惠券");
        }
        for (String s : split) {
            DiscountsPO discountsPO = selectOneById(s);
            //检查存在性
            if (discountsPO == null) {
                throw new CommonException("ID为" + s + "的优惠券不存在");
            }
            //检查是否拥有未使用(包括检查过期)
            if (!myDiscounts.contains(s)) {
                throw new CommonException("你没有名为 \"" + discountsPO.getName() + "\" 优惠券或该优惠券已过期");
            }
            //检查互斥
            if (DiscountEnums.EXCLUSIVE_TYPE_NONE.getCode().equals(discountsPO.getExclusiveType())) {
                //不互斥

            }else if (DiscountEnums.EXCLUSIVE_TYPE_ALL.getCode().equals(discountsPO.getExclusiveType())) {
                //全互斥
                if (discounts.size() > 1) {
                    throw new CommonException("\"" + discountsPO.getName() + "\" 优惠券与所选优惠券互斥");
                }
            }else {
                //部分互斥
                String[] exclusive = discountsPO.getExclusiveRange().split(",");
                for (String s1 : exclusive) {
                    if (discounts.contains(s1)) {
                        throw new CommonException("\"" + discountsPO.getName() + "\" 优惠券与所选优惠券互斥");
                    }
                }
            }
            //检查使用条件
            if (DiscountEnums.EFFECT_TYPE_MONEY.getCode().equals(discountsPO.getEffectType())) {
                if (discountsPO.getEffectCondition() > price) {
                    throw new CommonException("\"" + discountsPO.getName() + "\" 优惠券未满足使用条件");
                }
            }else if (DiscountEnums.EFFECT_TYPE_DAYS.getCode().equals(discountsPO.getEffectType())) {
                if (discountsPO.getEffectCondition() > days) {
                    throw new CommonException("\"" + discountsPO.getName() + "\" 优惠券未满足使用条件");
                }
            }
        }

        return false;
    }


}
