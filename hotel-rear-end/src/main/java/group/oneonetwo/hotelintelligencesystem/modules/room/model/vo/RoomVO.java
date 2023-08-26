package group.oneonetwo.hotelintelligencesystem.modules.room.model.vo;

import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("房间实体")
public class RoomVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("房间名称")
    private String name;

    @ApiModelProperty("房间类型")
    private String type;

    @ApiModelProperty("房间状态0未用,1已用,2已预订,3暂定")
    private Integer status;

    @ApiModelProperty("楼层")
    private String floor;

    @ApiModelProperty("房间朝向")
    private String direction;


    @ApiModelProperty("酒店id")
    private String hotelId;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ApiModelProperty("绑定订单id")
    private String orderId;

    @ApiModelProperty("房间类型名字")
    private String roomTypeName;

    @ApiModelProperty("是否为隔离")
    private Integer isIsolation;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getIsIsolation() {
        return isIsolation;
    }

    public void setIsIsolation(Integer isIsolation) {
        this.isIsolation = isIsolation;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "RoomVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", floor='" + floor + '\'' +
                ", direction='" + direction + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                ", orderId='" + orderId + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", isIsolation=" + isIsolation +
                ", page=" + page +
                '}';
    }

}
