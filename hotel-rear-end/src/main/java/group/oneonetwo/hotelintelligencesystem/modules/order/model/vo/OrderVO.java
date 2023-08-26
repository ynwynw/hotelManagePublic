package group.oneonetwo.hotelintelligencesystem.modules.order.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("订单实体")
public class OrderVO implements Serializable {

    @ExcelProperty(value = "订单ID",index = 0)
    @ApiModelProperty("主键")
    private String id;

    @ExcelIgnore
    @ApiModelProperty("客户id")
    private String customerId;

    @ExcelIgnore
    @ApiModelProperty("酒店id")
    private String hotelId;

    @ExcelIgnore
    @ApiModelProperty("房间类型")
    private String roomType;

    @ExcelProperty(value = "住店天数",index = 4)
    @ApiModelProperty("天数")
    private Integer days;

    @ExcelIgnore
    @ApiModelProperty("折扣类型")
    private String discount;

    @ExcelIgnore
    @ApiModelProperty("评论id")
    private String commentId;

    @ExcelProperty(value = "应付价格",index = 8)
    @ApiModelProperty("原价")
    private  String pay;

    @ExcelProperty(value = "实付价格",index = 9)
    @ApiModelProperty("实付价格")
    private String lastPay;

    @ExcelIgnore
    @ApiModelProperty("状态:0为未支付,1为已支付,2为已关闭")
    private String status;

    @ExcelProperty(value = "订单状态",index = 10)
    private String currentStatus;

    @ExcelProperty(value = "房间类型",index = 5)
    @ApiModelProperty("房间类型")
    private String roomTypeName;

    @ExcelProperty(value = "来源地",index = 3)
    @ApiModelProperty("省份")
    private String province;

    @ExcelIgnore
    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ExcelIgnore
    @ApiModelProperty("创建人")
    private String createBy;

    @ExcelProperty(value = "订单创建时间",index = 11)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ExcelIgnore
    @ApiModelProperty("更新人")
    private String updateBy;

    @ExcelIgnore
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ExcelIgnore
    private Page page;

    @ExcelProperty(value = "酒店名称",index = 1)
    @ApiModelProperty("酒店名")
    private String hotelName;

    @ExcelProperty(value = "客户用户名",index = 2)
    @ApiModelProperty("客户用户名")
    private String customerName;

    @ExcelIgnore
    @ApiModelProperty("起始时间")
    private String beginTime;

    @ExcelIgnore
    @ApiModelProperty("结束时间")
    private String endTime;

    @ExcelIgnore
    @ApiModelProperty("方式")
    private Integer way;

//    @ExcelIgnore
//    @ApiModelProperty("酒店优惠")
//    private String hotelDiscount;
//
//    @ExcelIgnore
//    @ApiModelProperty("个人优惠")
//    private String personalDiscount;

    @ExcelProperty(value = "入住时间",index = 6)
    @ApiModelProperty("实际入住时间")
    private Date checkInTime;

    @ExcelProperty(value = "退房时间",index = 7)
    @ApiModelProperty("实际退房时间")
    private Date checkOutTime;

    @ExcelIgnore
    @ApiModelProperty("预计入住时间")
    private Date estimatedCheckIn;

    @ExcelIgnore
    @ApiModelProperty("预计退房时间")
    private Date estimatedCheckOut;

    @ExcelIgnore
    @ApiModelProperty("房间名")
    private String roomName;

    @ExcelIgnore
    @ApiModelProperty("订单次数")
    private String counts;

    @ExcelIgnore
    @ApiModelProperty("支付方式")
    private Integer payWay;

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    //    public String getHotelDiscount() {
//        return hotelDiscount;
//    }
//
//    public void setHotelDiscount(String hotelDiscount) {
//        this.hotelDiscount = hotelDiscount;
//    }
//
//    public String getPersonalDiscount() {
//        return personalDiscount;
//    }
//
//    public void setPersonalDiscount(String personalDiscount) {
//        this.personalDiscount = personalDiscount;
//    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public Date getEstimatedCheckOut() {
        return estimatedCheckOut;
    }

    public void setEstimatedCheckOut(Date estimatedCheckOut) {
        this.estimatedCheckOut = estimatedCheckOut;
    }

    public Date getEstimatedCheckIn() {
        return estimatedCheckIn;
    }

    public void setEstimatedCheckIn(Date estimatedCheckIn) {
        this.estimatedCheckIn = estimatedCheckIn;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Page getPage() {
        return page;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", days=" + days +
                ", discount='" + discount + '\'' +
                ", pay='" + pay + '\'' +
                ", lastPay='" + lastPay + '\'' +
                ", status='" + status + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", province='" + province + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", page=" + page +
                ", hotelName='" + hotelName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", way=" + way +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
