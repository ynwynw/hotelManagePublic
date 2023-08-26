package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo;




import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableLogic;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liustart
 * @create 2022-04-19 10:56
 */
@ApiModel("隔离人员实体")
public class IsolationInfoVO  implements Serializable {
    /**
     * 隔离人员id
     */
    @ExcelProperty(value = "隔离人员id",index = 0)
    @ApiModelProperty("隔离人员id")
    private String id;

    /**
     * 隔离人员姓名
     */
    @ExcelProperty(value = "隔离人员姓名",index = 1)
    @ApiModelProperty("隔离人员姓名")
    private String name;

    @ExcelIgnore()
    @ApiModelProperty("隔离人员id")
    private String uId;

    @ExcelIgnore()
    @ApiModelProperty("用户名")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    /**
     * 身份证
     */
    @ExcelProperty(value = "身份证",index = 2)
    @ApiModelProperty("身份证")
    private String idCard;

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    @ExcelProperty(value = "隔离人员类型",index = 3)
    @ApiModelProperty("隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)")
    private Integer type;

    /**
     * 隔离人员电话
     */
    @ExcelProperty(value = "隔离人员电话",index = 4)
    @ApiModelProperty("隔离人员电话")
    private String phone;

    /**
     * 隔离隔离人员邮箱
     */
    @ExcelProperty(value = "隔离隔离人员邮箱",index = 5)
    @ApiModelProperty("隔离隔离人员邮箱")
    private String email;

    /**
     * 隔离酒店id
     */
    @ExcelIgnore
    @ApiModelProperty("隔离酒店id")
    private String hotelId;

    /**
     * 隔离酒店房间类型
     */
    @ExcelIgnore
    @ApiModelProperty("隔离酒店房间类型")
    private String roomType;

    /**
     * 隔离支付
     */
    @ExcelProperty(value = "隔离支付",index = 6)
    @ApiModelProperty("隔离支付")
    private Integer pay;

    /**
     * 入住时间
     */
    @ExcelProperty(value = "入住时间",index = 7)
    @ApiModelProperty("入住时间")
    private Date checkInTime;

    /**
     * 离开时间
     */
    @ExcelProperty(value = "退房时间",index = 8)
    @ApiModelProperty("退房时间")
    private Date checkOutTime;

    /**
     * 房间id
     */
    @ExcelProperty(value = "人员状态(0:正在隔离;1,解除隔离;2,阳性入院)",index = 9)
    @ApiModelProperty("人员状态(0:正在隔离;1,解除隔离;2,阳性入院)")
    private  Integer status;

    @ExcelIgnore
    @ApiModelProperty("房间id")
    private String roomId;

    @ExcelProperty(value = "酒店名字",index = 10)
    @ApiModelProperty("酒店名字")
    private String hotelName;

    /**
     * 房间名称
     */
    @ExcelProperty(value = "房间名称",index = 11)
    @ApiModelProperty("房间名称")
    private String roomName;

    @ExcelProperty(value = "房间类型",index = 12)
    @ApiModelProperty("房间类型")
    private String roomTypeName;

    /**
     * 省份
     */
    @ExcelProperty(value = "省份",index = 13)
    @ApiModelProperty("省份")
    private String province;

    /**
     * 城市
     */
    @ExcelProperty(value = "城市",index = 14)
    @ApiModelProperty("城市")
    private String city;

    @ExcelIgnore()
    @ApiModelProperty("来源(0,防疫人员分配,1,自己申报)")
    private Integer way;






    @ExcelIgnore
    @ApiModelProperty("起始时间")
    private String beginTime;

    @ExcelIgnore
    @ApiModelProperty("结束时间")
    private String endTime;

    @ExcelIgnore
    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    /**
     * 创建人
     */
    @ExcelIgnore
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间",index = 15)
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ExcelIgnore
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelIgnore
    @ApiModelProperty("更新时间")
    private Date updateTime;



    @ExcelIgnore()
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

    /**
     * 隔离人员id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    @Override
    public String toString() {
        return "IsolationInfoVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", type=" + type +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", pay='" + pay + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", status=" + status +
                ", roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", page=" + page +
                '}';
    }
}
