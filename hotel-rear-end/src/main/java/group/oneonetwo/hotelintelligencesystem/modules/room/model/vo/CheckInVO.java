package group.oneonetwo.hotelintelligencesystem.modules.room.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author 文
 */
public class CheckInVO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("客户id")
    private String customerId;

    @ApiModelProperty("来访地")
    private String province;

    @ApiModelProperty("预计入住时间")
    private Date checkInTime;

    @ApiModelProperty("预计退房时间")
    private Date estimatedCheckOut;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getEstimatedCheckOut() {
        return estimatedCheckOut;
    }

    public void setEstimatedCheckOut(Date estimatedCheckOut) {
        this.estimatedCheckOut = estimatedCheckOut;
    }
}
