package group.oneonetwo.hotelintelligencesystem.modules.review.service.impl;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.config.AlipayConfig;
import group.oneonetwo.hotelintelligencesystem.enums.AlipayEnums;
import group.oneonetwo.hotelintelligencesystem.enums.BalanceHandleMode;
import group.oneonetwo.hotelintelligencesystem.enums.OrderEnums;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.po.ReviewPO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.service.ReviewService;
import group.oneonetwo.hotelintelligencesystem.modules.review.dao.ReviewMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 文
* @description 针对表【review】的数据库操作Service实现
* @createDate 2022-04-19 10:40:37
*/
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IRoomService roomService;

    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @Autowired
    IsolationInfoService isolationInfoService;

    @Autowired
    ReviewService reviewService;


    @Autowired
    WalletService walletService;

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IUserService userService;

    @Autowired
    AlipayConfig alipayConfig;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    @Override
    public ReviewPO selectOneById(String id) {
        ReviewPO reviewPO = reviewMapper.selectById(id);
        return reviewPO;
    }

    @Override
    public ReviewVO add(ReviewVO reviewVO) {
        if(reviewVO==null){
            throw new SavaException("插入隔离人员信息失败:实体为空");
        }
        ReviewPO reviewPO = new ReviewPO();
        BeanUtils.copyProperties(reviewVO,reviewPO);
        int insert=reviewMapper.insert(reviewPO);
        reviewVO.setId(reviewPO.getId());
        if(insert>0){
            return reviewVO;
        }
        throw new SavaException("增加审批信息失败");
    }

    @Override
    public ReviewVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        ReviewPO reviewPO = reviewMapper.selectById(id);
        ReviewVO reviewVO = new ReviewVO();
        if(reviewPO!=null){
            BeanUtils.copyProperties(reviewPO,reviewVO);
        }
        return reviewVO;
    }

    @Override
    public ReviewVO save(ReviewVO reviewVO) {
        if (reviewVO==null){
            throw new CommonException(501,"隔离人员信息实体为空");
        }
        ReviewVO check = selectOneByIdReturnVO(reviewVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+reviewVO.getId()+"的数据");
        }
        ReviewPO reviewPO = new ReviewPO();
        BeanUtils.copyProperties(reviewVO,reviewPO);
        int save=reviewMapper.updateById(reviewPO);
        if(save>0){
            return reviewVO;
        }
        throw new SavaException("更改审批信息失败");
    }

    @Override
    public Integer deleteById(String id) {
        ReviewVO check = selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=reviewMapper.deleteById(id);
        return i;

    }

    @Override
    public Page<ReviewVO> getPage(ReviewVO reviewVO) {
        Page<ReviewPO> page=new Page<>(reviewVO.getPage().getPage(),reviewVO.getPage().getSize());
        return reviewMapper.getPage(page,reviewVO);

    }
    //id
    @Override
    public ReviewVO getCheck(ReviewVO reviewVO) {
        reviewVO.setUid(authUtils.getUid());
        ReviewVO add = null;
        if(reviewVO.getType()==0 || reviewVO.getType()==1){
            reviewVO.setPayStatus(2);
            add = add(reviewVO);
        }else {

            if(userService.selectOneById(reviewVO.getUid())==null){
                throw new CommonException(501,"账户未注册");
            }
            RoomTypePO roomTypePO = roomTypeServeice.selectOneById(reviewVO.getRoomType());
            Integer isolationFee = roomTypePO.getIsolationFee();
            reviewVO.setPayStatus(0);
            reviewVO.setTotalFee((int)isolationFee*14);
            add = add(reviewVO);
        }
        return add;
    }

    /**
     * 支付自费隔离
     * @param id
     * @param walletPwd
     */
    @Override
    public void payDeclaration(String id, String walletPwd) {
        ReviewPO reviewPO = selectOneById(id);
        //如果免费类型或已付费类型直接跳过
        if (reviewPO.getPayStatus() == 2 || reviewPO.getPayStatus() == 1) {
            return;
        }
        WalletPO walletPO = walletService.getWalletPOByUid(authUtils.getUid());
        boolean matches = bCryptPasswordEncoder.matches(walletPwd, walletPO.getPassword());
        if (matches) {
            walletService.editBalance(BalanceHandleMode.REDUCE.getCode(), Double.valueOf(reviewPO.getTotalFee()));

            //更改支付状态信息
            ReviewVO changeStatus = new ReviewVO();
            changeStatus.setPayStatus(1);
            save(changeStatus);
        }else {
            throw new CommonException("支付密码错误");
        }

    }

    //    暂时注释支付宝相关代码
    /**
     * 支付宝支付隔离费用
     * @param id
     */
    @Override
    public String payDeclarationForAlipay(String id) throws JSONException, AlipayApiException {
        ReviewPO reviewPO = selectOneById(id);
        //如果免费类型或已付费类型直接跳过
        if (reviewPO.getPayStatus() == 2 || reviewPO.getPayStatus() == 1) {
            throw new CommonException("此次隔离免费或已支付,请直接输入钱包密码确认申请");
        }
        //请求创建支付二维码
        AlipayClient alipayClient = alipayConfig.getAlipayClient();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl("");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);
        bizContent.put("total_amount", reviewPO.getTotalFee());
        bizContent.put("subject", "智慧酒店-隔离费用");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("qr_pay_mode",1);
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        logger.info("alipay-response:",response.getBody());
        if(response.isSuccess()){
            logger.info("prePayOrderForAlipay调用成功");
            return response.getBody().toString();
        } else {
            logger.warn("prePayOrderForAlipay调用失败");
        }
        return null;
    }

    /**
     * 检查支付宝支付状态
     * @param id
     */
    @Override
    public Boolean checkDeclarationPayStatusForAlipay(String id) throws AlipayApiException, JSONException {
        ReviewPO reviewPO = selectOneById(id);
        //如果免费类型或已付费类型直接跳过
        if (reviewPO.getPayStatus() == 2 || reviewPO.getPayStatus() == 1) {
            return true;
        }

        //请求查询
        AlipayClient alipayClient = alipayConfig.getAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);//订单号
        //bizContent.put("trade_no", "2014112611001004680073956707");//交易号
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            logger.info("checkPayOrderForAlipay调用成功");
            Gson gson = new Gson();
            Map<String,String> responseMap = (Map<String, String>) gson.fromJson(response.getBody(), new HashMap<String, String>().getClass()).get("alipay_trade_query_response");
            //交易成功
            if (AlipayEnums.TRADE_SUCCESS.equals(responseMap.get("trade_status"))){
                //更改支付状态信息
                ReviewVO changeStatus = new ReviewVO();
                changeStatus.setPayStatus(1);
                changeStatus.setId(id);
                save(changeStatus);

                logger.info("交易成功");
                return true;
            }
        }else {
            logger.warn("checkPayOrderForAlipay调用失败");
        }
        throw new CommonException("支付失败,请重试!");
    }

    //admin  id  review_status
    @Override
    public void getReviews(ReviewVO reviewVO) {
        if(reviewVO.getReviewStatus()==2){
            if(reviewVO.getRemark()==null || reviewVO.getRemark()=="") {
                throw new CommonException(501, "拒绝的理由不能为空");
            }
            //这里有个如果拒绝后有个打回钱的流程
            ReviewPO reviewPO = selectOneById(reviewVO.getId());
            if(reviewPO.getType()==2 || reviewPO.getType()==3){
                RoomTypePO roomTypePO = roomTypeServeice.selectOneById(reviewPO.getRoomType());
                WalletPO walletPO = walletService.getWalletPOByUid(reviewPO.getUid());
                walletPO.setBalance(walletPO.getBalance()+roomTypePO.getIsolationFee());
                walletService.save(walletPO);
            }
            reviewPO.setRemark(reviewVO.getRemark());
            reviewPO.setReviewStatus(2);
            ReviewVO reviewVO1 = new ReviewVO();
            BeanUtils.copyProperties(reviewPO,reviewVO1);
            reviewService.save(reviewVO1);
            return;
        }

        ReviewPO reviewPO = selectOneById(reviewVO.getId());


        RoomVO roomVO = roomService.isolationCheckIn(reviewVO.getHotelId(), reviewVO.getRoomType(), null);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        isolationInfoVO.setName(reviewPO.getName());
        isolationInfoVO.setuId(reviewPO.getUid());
        isolationInfoVO.setIdCard(reviewPO.getIdCard());
        isolationInfoVO.setType(reviewPO.getType());
        isolationInfoVO.setPhone(reviewPO.getPhone());
        isolationInfoVO.setEmail(reviewPO.getEmail());
        isolationInfoVO.setHotelId(reviewPO.getHotelId());
        isolationInfoVO.setRoomType(roomVO.getType());
        isolationInfoVO.setPay(reviewPO.getTotalFee());
        isolationInfoVO.setCheckInTime(reviewPO.getCheckInTime());
        isolationInfoVO.setCheckOutTime(reviewPO.getCheckOutTime());
        isolationInfoVO.setRoomId(roomVO.getId());
        isolationInfoVO.setRoomName(roomVO.getName());
        isolationInfoVO.setProvince(reviewPO.getProvince());
        isolationInfoVO.setCity(reviewPO.getCity());
        isolationInfoVO.setStatus(0);
        isolationInfoService.add(isolationInfoVO);
        ReviewVO reviewVO1 = new ReviewVO();
        reviewPO.setReviewStatus(1);
        BeanUtils.copyProperties(reviewPO,reviewVO1);
        reviewService.save(reviewVO1);

    }

    @Override
    public ReviewPO selectByUID(String id) {
        QueryWrapper<ReviewPO> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",id);
        List<ReviewPO> reviewPOS = reviewMapper.selectList(wrapper);
        return reviewPOS.get(0);
    }

    @Override
    public Page<ReviewVO> my(ReviewVO reviewVO) {
        String uid = authUtils.getUid();
        reviewVO.setUid(uid);
        Page<ReviewVO> page = this.getPage(reviewVO);
        return page;
    }


}
