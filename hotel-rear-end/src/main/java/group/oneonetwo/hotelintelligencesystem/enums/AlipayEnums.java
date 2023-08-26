package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 * @description: TODO
 * @date 2022/6/22 15:22
 */
public enum AlipayEnums {

    WAIT_BUYER_PAY,//（交易创建，等待买家付款）
    TRADE_CLOSED,//（未付款交易超时关闭，或支付完成后全额退款）
    TRADE_SUCCESS,//（交易支付成功）
    TRADE_FINISHED//（交易结束，不可退款）
    ;

    public boolean equals(String tar) {
        return super.toString().equals(tar);
    }
}
