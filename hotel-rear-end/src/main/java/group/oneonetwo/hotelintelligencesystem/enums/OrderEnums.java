package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 * @description: 折扣相关枚举类
 * @date 2022-05-07 16:26:55
 */
public enum OrderEnums {
    //status
    STATUS_UNPAID(0),
    STATUS_PAID(1),
    STATUS_CLOSE(2),
    STATUS_LIVING(3),
    STATUS_DONE(4),
    //way
    WAY_OFFLINE(0),
    WAY_ONLINE(1),
    //pay_way
    PAY_WAY_SYS(0),
    PAY_WAY_ALIPAY(1),
    ;

    private Integer code;

    OrderEnums(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
