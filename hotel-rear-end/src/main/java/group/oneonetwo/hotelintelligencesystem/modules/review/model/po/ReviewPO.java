package group.oneonetwo.hotelintelligencesystem.modules.review.model.po;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName review
 */
@TableName(value ="review")
public class ReviewPO implements Serializable {
    /**
     * 申报id
     */
    @TableId("id")
    private String id;


    @TableField("u_id")
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 申报名称
     */
    @TableField("name")
    private String name;

    /**
     * 身份证
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    @TableField("type")
    private Integer type;

    /**
     * 隔离人员电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 隔离隔离人员邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 隔离酒店id
     */
    @TableField("hotel_id")
    private String hotelId;

    /**
     * 入住时间
     */
    @TableField("check_in_time")
    private Date checkInTime;

    /**
     * 离开时间
     */
    @TableField("check_out_time")
    private Date checkOutTime;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 隔离酒店房间类型
     */
    @TableField("room_type")
    private String roomType;

    /**
     * (0未审核,1审核通过,2,拒绝)
     */
    @TableField("review_status")
    private Integer reviewStatus;

    /**
     * 拒绝的理由
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("total_fee")
    private Integer totalFee;

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 创建人
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField("pay_status")
    private Integer payStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 申报id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
