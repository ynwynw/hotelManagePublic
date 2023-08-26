package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 * @description: 房间状态
 * @date 2022/4/20 18:26
 */
public enum RoomStatus {
    UNUSED(0),
    USED(1),
    BOOKED(2),
    TENTATIVE(3),
    STERILIZE(4);

    private Integer code;

    RoomStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
