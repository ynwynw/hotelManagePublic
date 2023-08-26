package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 * @description: 余额操作模式
 * @date 2022/4/20 18:26
 */
public enum BalanceHandleMode {
    INIT(0),
    ADD(1),
    REDUCE(2);

    private Integer code;

    BalanceHandleMode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
