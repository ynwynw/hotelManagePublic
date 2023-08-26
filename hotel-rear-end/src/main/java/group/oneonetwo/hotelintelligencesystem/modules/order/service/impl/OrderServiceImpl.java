package group.oneonetwo.hotelintelligencesystem.modules.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.config.AlipayConfig;
import group.oneonetwo.hotelintelligencesystem.enums.AlipayEnums;
import group.oneonetwo.hotelintelligencesystem.enums.BalanceHandleMode;
import group.oneonetwo.hotelintelligencesystem.enums.DiscountEnums;
import group.oneonetwo.hotelintelligencesystem.enums.OrderEnums;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.order.dao.OrderMapper;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.vo.WalletVO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import group.oneonetwo.hotelintelligencesystem.tools.TimeUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author 文
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    IUserService userService;

    @Autowired
    IHotelService hotelService;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IDiscountsService discountsService;

    @Autowired
    IRoomService roomService;

    @Autowired
    WalletService walletService;

    @Autowired
    IDiscountUserService discountUserService;

    @Autowired
    AlipayConfig alipayConfig;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    @Override
    public OrderPO add(OrderVO orderVO){
        if(orderVO==null){
            throw new SavaException("插入订单失败:订单实体为空");
        }
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        int insert=orderMapper.insert(orderPO);
        if(insert>0){
            return orderPO;
        }
        throw new SavaException("插入订单失败");
    }

    @Override
    public OrderVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        OrderPO orderPO=orderMapper.selectById(id);
        OrderVO orderVO=new OrderVO();
        if(orderPO!=null){
            BeanUtils.copyProperties(orderPO,orderVO);
        }
        return orderVO;
    }

    @Override
    public OrderPO save(OrderVO orderVO){
        if (orderVO==null){
            throw new CommonException(501,"订单实体为空");
        }
        OrderVO check=selectOneByIdReturnVO(orderVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+orderVO.getId()+"的数据");
        }
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        int save=orderMapper.updateById(orderPO);
        if(save>0){
            return orderPO;
        }
        throw new SavaException("更改订单失败");
    }
    @Override
    public Integer deleteById(String id){
        OrderVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=orderMapper.deleteById(id);
        return i;
   }

    @Override
    public OrderPO selectOneById(String id){
        OrderPO orderPO=orderMapper.selectById(id);
        return orderPO;
    }

    @Override
    public OrderVO saveone(OrderVO orderVO){
        OrderPO save=save(orderVO);
        BeanUtils.copyProperties(save,orderVO);
        return orderVO;
    }

    @Override
    public OrderVO addOne(OrderVO orderVO){
        OrderPO st=add(orderVO);
        BeanUtils.copyProperties(st,orderVO);
        return orderVO;
    }

    @Override
    public Page<OrderVO> getPage(OrderVO orderVO){
        Page<OrderPO> page=new Page<>(orderVO.getPage().getPage(),orderVO.getPage().getSize());
        return orderMapper.getPage(page,orderVO);
    }

    @Override
    public List<OrderVO> getAllList(OrderVO orderVO) {
        String authority = authUtils.getRole();
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authUtils.getUid());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                HotelVO hotelVO = new HotelVO();
                hotelVO.setDeptId(userVO.getDept());
                hotelVO = hotelService.selectOneByDeptId(userVO.getDept());
                if (hotelVO == null) {
                    throw new CommonException(500,"该用户未绑定酒店");
                }
                orderVO.setHotelId(hotelVO.getId());
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        return orderMapper.getAllList(orderVO);
    }

    @Override
    public void payOrder(String orderId, String walletPwd) {
        //查询订单
        OrderVO orderVO = selectOneByIdReturnVO(orderId);
        if (orderVO.getPayWay() != null) {
            throw new CommonException("该订单已被支付,请刷新页面获取订单最新状态!");
        }
        if (!(OrderEnums.STATUS_UNPAID.getCode().toString().equals(orderVO.getStatus()))) {
            throw new CommonException("该订单不允许该操作");
        }
        //查询钱包(顺便验证密码了)
        WalletVO wallet = walletService.getWallet(walletPwd);
        if (wallet.getBalance() < Double.parseDouble(orderVO.getLastPay())) {
            throw new CommonException("余额不足!");
        }
        walletService.editBalance(BalanceHandleMode.REDUCE.getCode(), Double.valueOf(orderVO.getLastPay()));
        //分配房间
        RoomVO roomVO = new RoomVO();
        roomVO.setType(orderVO.getRoomType());
        roomVO.setOrderId(orderVO.getId());
        boolean assignRoom = roomService.assignRoom(roomVO);
        if (!assignRoom) {
            throw new CommonException("已没同类型房间,请选择其他类型房间进行入住");
        }

        //更新订单状态
        OrderVO update = new OrderVO();
        update.setId(orderVO.getId());
        update.setStatus(OrderEnums.STATUS_PAID.getCode().toString());
        update.setPayWay(OrderEnums.PAY_WAY_SYS.getCode());
        OrderPO save = save(update);
    }

//    暂时注释支付宝相关代码
    @Override
    public String prePayOrderForAlipay(String orderId) throws AlipayApiException, JSONException {
        OrderPO orderPO = orderMapper.selectById(orderId);
        //防止重复支付
        if (orderPO.getPayWay() != null) {
            throw new CommonException("该订单已被支付,请刷新页面获取订单最新状态!");
        }

        //请求创建支付二维码
        AlipayClient alipayClient = alipayConfig.getAlipayClient();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl("");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", orderPO.getLastPay());
        bizContent.put("subject", "智慧酒店-预订房间");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("qr_pay_mode",1);
    //bizContent.put("time_expire", "2022-08-01 22:00:00");

//        // 商品明细信息，按需传入
//        RoomTypePO roomTypePO = roomTypeServeice.selectOneById(orderPO.getRoomType());
//        JSONArray goodsDetail = new JSONArray();
//        JSONObject goods1 = new JSONObject();
//        goods1.put("goods_id", orderPO.getHotelId() + orderPO.getRoomType());
//        goods1.put("goods_name", hotelService.selectOneById(orderPO.getHotelId()).getName()
//                + "-" + roomTypePO.getName());
//        goods1.put("quantity", orderPO.getDays());
//        goods1.put("price", roomTypePO.getFee());
//        goodsDetail.put(goods1);
//        bizContent.put("goods_detail", goodsDetail);

    //// 扩展信息，按需传入
    //JSONObject extendParams = new JSONObject();
    //extendParams.put("sys_service_provider_id", "2088511833207846");
    //bizContent.put("extend_params", extendParams);

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

    @Override
    public Boolean checkPayOrderForAlipay(String orderId) throws Exception {
        OrderVO orderVO = selectOneByIdReturnVO(orderId);
        if (orderVO.getPayWay() != null) {
            throw new CommonException("该订单已被支付,请刷新页面获取订单最新状态!");
        }


        //请求查询
        AlipayClient alipayClient = alipayConfig.getAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);//订单号
        //bizContent.put("trade_no", "2014112611001004680073956707");//交易号
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("checkPayOrderForAlipay调用成功");
            Gson gson = new Gson();
            Map<String,String> responseMap = (Map<String, String>) gson.fromJson(response.getBody(), new HashMap<String, String>().getClass()).get("alipay_trade_query_response");
            //交易成功
            if (AlipayEnums.TRADE_SUCCESS.equals(responseMap.get("trade_status"))){
                //分配房间
                RoomVO roomVO = new RoomVO();
                roomVO.setType(orderVO.getRoomType());
                roomVO.setOrderId(orderVO.getId());
                boolean assignRoom = roomService.assignRoom(roomVO);
                if (!assignRoom) {
                    cancelOrderForAlipay(orderId);
                    throw new CommonException("已没同类型房间,请选择其他类型房间进行入住!交易金额已原路返回!");
                }
                //更新订单状态
                OrderVO update = new OrderVO();
                update.setId(orderVO.getId());
                update.setStatus(OrderEnums.STATUS_PAID.getCode().toString());
                update.setPayWay(OrderEnums.PAY_WAY_ALIPAY.getCode());
                OrderPO save = save(update);
                logger.info("交易成功");
                return true;
            }
        } else {
            logger.warn("checkPayOrderForAlipay调用失败");
        }
        throw new CommonException("支付失败,请重试!");
    }

    @Override
    public String cancelOrderForAlipay(String orderId) throws Exception {
        OrderPO thisOrder = baseCancelOrder(orderId);
        AlipayClient alipayClient = alipayConfig.getAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", orderId);
        bizContent.put("refund_amount", thisOrder.getLastPay());
//        bizContent.put("out_request_no", "HZ01RF001");

        //// 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("cancelOrderForAlipay调用成功");
        } else {
            logger.warn("cancelOrderForAlipay调用失败");
            throw new CommonException("退款失败,请重试!");
        }
        return "退款成功,请在支付宝App查看退款详情!";
    }


    @Override
    public OrderVO createNewOrder(OrderVO orderVO) {
        if (orderVO.getEstimatedCheckIn() == null || orderVO.getEstimatedCheckOut() == null) {
            throw new CommonException("入住/退房时间参数为空");
        }
        if (WStringUtils.isBlank(orderVO.getProvince())) {
            throw new CommonException("来访地参数为空");
        }
        if (WStringUtils.isBlank(orderVO.getRoomType())) {
            throw new CommonException("房间类型参数为空");
        }

        //预创建,获取订单id
        orderVO = addOne(orderVO);

        orderVO.setStatus(OrderEnums.STATUS_UNPAID.getCode().toString());
        orderVO.setWay(OrderEnums.WAY_ONLINE.getCode());


        //格式化时间
        orderVO.setEstimatedCheckIn(TimeUtils.setSplitTime(orderVO.getEstimatedCheckIn()));
        orderVO.setEstimatedCheckOut(TimeUtils.setSplitTime(orderVO.getEstimatedCheckOut()));

        //计算天数
        orderVO.setDays(TimeUtils.daysBetween(orderVO.getEstimatedCheckIn(),orderVO.getEstimatedCheckOut(),"ceil"));

        //获取订单房间类型
        RoomTypeVO roomTypeVO = roomTypeServeice.selectOneByIdReturnVO(orderVO.getRoomType());
        if (roomTypeVO == null) {
            throw new CommonException("找不到对应的房间类型");
        }
        orderVO.setHotelId(roomTypeVO.getHotelId());
        //计算价钱
        double[] pays = discountsService.countPay(orderVO.getDays(), roomTypeVO.getFee(),orderVO.getDiscount());
        orderVO.setPay(String.valueOf(pays[0]));
        orderVO.setLastPay(String.valueOf(pays[1]));

        OrderPO save = save(orderVO);
        BeanUtils.copyProperties(save,orderVO);


        return orderVO;
    }

    /**
     * 基础取消订单方法
     * @param orderId
     * @return 返回当前订单便于后续操作
     */
    private OrderPO baseCancelOrder(String orderId) {
        RoomVO roomVO = new RoomVO();
        roomVO.setOrderId(orderId);
        roomService.cancelRoom(roomVO);
        OrderVO orderVO = new OrderVO();
        orderVO.setStatus(OrderEnums.STATUS_CLOSE.getCode().toString());
        orderVO.setId(orderId);
        OrderPO save = save(orderVO);
        OrderPO thisOrder = orderMapper.selectById(save.getId());
        discountUserService.changeDiscountStatus(thisOrder.getDiscount(), DiscountEnums.DISCOUNT_USER_UNUSED.getCode());
        return thisOrder;
    }


    @Override
    public String cancelOrder(String id) {
        OrderPO thisOrder = baseCancelOrder(id);
        //退款和优惠券
        walletService.editBalance(BalanceHandleMode.ADD.getCode(), Double.valueOf(thisOrder.getLastPay()));
        return "取消订单成功,退款" + thisOrder.getLastPay() + "元将在0-3个工作日内原路退还。";
    }

    @Override
    public  Page<OrderVO> my(OrderVO orderVO){
        String role = authUtils.getRole();
        switch (role) {
            case "admin":break;
            case "hotel_admin":orderVO.setHotelId(authUtils.getUserHotelId());break;
            default:
                orderVO.setCustomerId(authUtils.getUid());
                break;
        }
        Page<OrderVO> page = getPage(orderVO);
        return page;
    }

    @Override
    public  List<OrderVO> count(OrderVO orderVO){
        orderVO.setCustomerId(authUtils.getUid());
        return orderMapper.getcount(orderVO);
    }

    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @Override
    public void randomOrder() {
        String[] provinces = {"河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省",
                "浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
                "广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省",
                "台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区",
                "新疆维吾尔自治区","北京市","天津市","上海市","重庆市","香港特别行政区","澳门特别行政区"};
        String[] members = {"3","4","5","6","7","1506647494363246593"};
        String hotel = "1";
        Integer[] ways = {1,2};
        String[] roomTypes = { "1463764265293885441","1463764265293885441",
                "1463890996549947394", "1463890996549947394","1463890996549947394","1463890996549947394",
                "1463891142348148737","1463891142348148737","1463891142348148737",
                "1470613302300102658","1470613302300102658",
                "1470613446756126722"};
        for (int i = 0; i < 350; i++) {
            OrderVO orderVO = new OrderVO();
            Random random = new Random();
            orderVO.setHotelId(hotel);
            orderVO.setProvince(provinces[random.nextInt(provinces.length)]);
            orderVO.setCustomerId(members[random.nextInt(members.length)]);
            orderVO.setRoomType(roomTypes[random.nextInt(roomTypes.length)]);
            orderVO.setStatus("1");
            orderVO.setDays(random.nextInt(5) + 1);
            orderVO.setWay(ways[random.nextInt(2)]);
            RoomTypeVO roomTypeVO = roomTypeServeice.selectOneByIdReturnVO(orderVO.getRoomType());
            orderVO.setPay(String.valueOf(roomTypeVO.getFee() * orderVO.getDays()));
            orderVO.setLastPay(orderVO.getPay());
            add(orderVO);
        }
    }
}
