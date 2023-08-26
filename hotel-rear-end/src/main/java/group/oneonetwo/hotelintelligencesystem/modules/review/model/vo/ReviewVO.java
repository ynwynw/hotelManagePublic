package group.oneonetwo.hotelintelligencesystem.modules.review.model.vo;



import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liustart
 * @create 2022-04-19 11:22
 */
@ApiModel("申报审核实体")
public class ReviewVO implements Serializable {

    @ApiModelProperty("申报id")
    private String id;

    /**
     * 申报名称
     */
    @ApiModelProperty("用户id")
    private String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @ApiModelProperty("申报名称")
    private String name;

    @ApiModelProperty("总费用)")
    private Integer totalFee;

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 身份证
     */
    @ApiModelProperty("身份证")
    private String idCard;

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    @ApiModelProperty("隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)")
    private Integer type;

    /**
     * 隔离人员电话
     */
    @ApiModelProperty("隔离人员电话")
    private String phone;

    /**
     * 隔离隔离人员邮箱
     */
    @ApiModelProperty("隔离隔离人员邮箱")
    private String email;

    /**
     * 隔离酒店id
     */
    @ApiModelProperty("隔离酒店id")
    private String hotelId;

    /**
     * 入住时间
     */
    @ApiModelProperty("入住时间")
    private Date checkInTime;

    /**
     * 离开时间
     */
    @ApiModelProperty("离开时间")
    private Date checkOutTime;

    /**
     * 省份
     */
    @ApiModelProperty("省份")
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty("城市")
    private String city;

    /**
     * 隔离酒店房间类型
     */
    @ApiModelProperty("隔离酒店房间类型")
    private String roomType;

    /**
     * (0未审核,1审核通过,2,拒绝)
     */
    @ApiModelProperty("(0未审核,1审核通过,2,拒绝)")
    private Integer reviewStatus;

    /**
     * 拒绝的理由
     */
    @ApiModelProperty("拒绝的理由")
    private String remark;

    private Integer payStatus;

    /**
     * 是否删除
     */

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("起始时间")
    private String beginTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

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

    @Override
    public String toString() {
        return "ReviewVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalFee=" + totalFee +
                ", idCard='" + idCard + '\'' +
                ", type=" + type +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", roomType='" + roomType + '\'' +
                ", reviewStatus=" + reviewStatus +
                ", remark='" + remark + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", page=" + page +
                '}';
    }
}
