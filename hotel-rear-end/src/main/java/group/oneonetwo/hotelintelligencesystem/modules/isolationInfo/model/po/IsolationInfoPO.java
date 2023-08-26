package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName isolation_info
 */
@TableName(value ="isolation_info")
public class IsolationInfoPO implements Serializable {
    /**
     * 隔离人员id
     */
    @TableId("id")
    private String id;

    /**
     * 隔离人员姓名
     */

    @TableField("u_id")
    private  String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

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
     * 隔离酒店房间类型
     */
    @TableField("room_type")
    private String roomType;

    /**
     * 隔离支付
     */
    @TableField("pay")
    private Integer pay;

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
     * 房间id
     */
    @TableField("room_id")
    private String roomId;

    /**
     * 房间名称
     */
    @TableField("room_name")
    private String roomName;

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

    @TableField("way")
    private Integer way;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

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
     * 更新人
     *
     */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;


    @TableField("status")
    private  Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    /**
     * 隔离人员id
     */

    public String getId() {
        return id;
    }

    /**
     * 隔离人员id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 隔离人员姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 隔离人员姓名
     */
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
