package group.oneonetwo.hotelintelligencesystem.modules.order.model.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@TableName("orders")
public class OrderPO implements Serializable {

    @TableId(value = "id")
    private String id;

    @TableField("customer_id")
    private String customerId;

    @TableField("hotel_id")
    private String hotelId;

    @TableField("room_type")
    private String roomType;

    @TableField("days")
    private Integer days;

    @TableField("discount")
    private String discount;

    @TableField("comment_id")
    private String commentId;

    @TableField("pay")
    private  String pay;

    @TableField("last_pay")
    private String lastPay;

    @TableField("status")
    private String status;

    @TableField("province")
    private String province;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("way")
    private Integer way;

    @TableField("check_in_time")
    private Date checkInTime;

    @TableField("check_out_time")
    private Date checkOutTime;

    @TableField("estimated_check_in")
    private Date estimatedCheckIn;

    @TableField("estimated_check_out")
    private Date estimatedCheckOut;

    @TableField("room_name")
    private  String roomName;

    @TableField("pay_way")
    private Integer payWay;

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Date getEstimatedCheckIn() {
        return estimatedCheckIn;
    }

    public void setEstimatedCheckIn(Date estimatedCheckIn) {
        this.estimatedCheckIn = estimatedCheckIn;
    }

    public Date getEstimatedCheckOut() {
        return estimatedCheckOut;
    }

    public void setEstimatedCheckOut(Date estimatedCheckOut) {
        this.estimatedCheckOut = estimatedCheckOut;
    }

    public Integer getWay() {
        return way;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getLastPay() {
        return lastPay;
    }

    public void setLastPay(String lastPay) {
        this.lastPay = lastPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
