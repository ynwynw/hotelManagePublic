package group.oneonetwo.hotelintelligencesystem.modules.room.model.vo;

import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class DetailVO implements Serializable{
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

    @ApiModelProperty("绑定订单id")
    private String orderId;

    @ApiModelProperty("是否为隔离")
    private Integer isIsolation;


    //room_type

    @ApiModelProperty("主键")
    private String rtId;

    @ApiModelProperty("房间名称")
    private String rtName;

    @ApiModelProperty("床类型")
    private String bedType;

    @ApiModelProperty("床位")
    private Integer bed;

    @ApiModelProperty("最大容量")
    private Integer maxLoad;

    @ApiModelProperty("是否有窗(0:没有,1:有)")
    private Integer haveWindow;

    @ApiModelProperty("网络情况(0:没有,1:有)")
    private Integer network;

    @ApiModelProperty("早餐情况((0:没有,1:有))")
    private Integer haveBreakfast;

    @ApiModelProperty("媒体情况")
    private String media;

    @ApiModelProperty("浴室配套")
    private String rushroomSupporting;

    @ApiModelProperty("食品相关配套")
    private String foodRelated;

    @ApiModelProperty("其他费用")
    private Integer otherFee;

    @ApiModelProperty("封面图")
    private String cover;

    @ApiModelProperty("费用")
    private Integer fee;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    //bed

    @ApiModelProperty("主键")
    private String BedId;

    @ApiModelProperty("床的名字")
    private String bedName;

    @ApiModelProperty("床的最大容量")
    private String bedMaxLoad;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("硬度(0:超软,1:偏软,2:适中,3:偏硬,4:硬")
    private String hardness;

    private Page page;

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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRtId() {
        return rtId;
    }

    public void setRtId(String rtId) {
        this.rtId = rtId;
    }

    public String getRtName() {
        return rtName;
    }

    public void setRtName(String rtName) {
        this.rtName = rtName;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public Integer getBed() {
        return bed;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }

    public Integer getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Integer maxLoad) {
        this.maxLoad = maxLoad;
    }

    public Integer getHaveWindow() {
        return haveWindow;
    }

    public void setHaveWindow(Integer haveWindow) {
        this.haveWindow = haveWindow;
    }

    public Integer getNetwork() {
        return network;
    }

    public void setNetwork(Integer network) {
        this.network = network;
    }

    public Integer getHaveBreakfast() {
        return haveBreakfast;
    }

    public void setHaveBreakfast(Integer haveBreakfast) {
        this.haveBreakfast = haveBreakfast;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getRushroomSupporting() {
        return rushroomSupporting;
    }

    public void setRushroomSupporting(String rushroomSupporting) {
        this.rushroomSupporting = rushroomSupporting;
    }

    public String getFoodRelated() {
        return foodRelated;
    }

    public void setFoodRelated(String foodRelated) {
        this.foodRelated = foodRelated;
    }

    public Integer getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Integer otherFee) {
        this.otherFee = otherFee;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getBedId() {
        return BedId;
    }

    public void setBedId(String bedId) {
        BedId = bedId;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getBedMaxLoad() {
        return bedMaxLoad;
    }

    public void setBedMaxLoad(String bedMaxLoad) {
        this.bedMaxLoad = bedMaxLoad;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getHardness() {
        return hardness;
    }

    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getIsIsolation() {
        return isIsolation;
    }

    public void setIsIsolation(Integer isIsolation) {
        this.isIsolation = isIsolation;
    }

    @Override
    public String toString() {
        return "DetailVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", floor='" + floor + '\'' +
                ", direction='" + direction + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", rtId='" + rtId + '\'' +
                ", rtName='" + rtName + '\'' +
                ", bedType='" + bedType + '\'' +
                ", bed=" + bed +
                ", maxLoad=" + maxLoad +
                ", haveWindow=" + haveWindow +
                ", network=" + network +
                ", haveBreakfast=" + haveBreakfast +
                ", media='" + media + '\'' +
                ", rushroomSupporting='" + rushroomSupporting + '\'' +
                ", foodRelated='" + foodRelated + '\'' +
                ", otherFee=" + otherFee +
                ", cover='" + cover + '\'' +
                ", fee=" + fee +
                ", sort=" + sort +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", BedId='" + BedId + '\'' +
                ", bedName='" + bedName + '\'' +
                ", bedMaxLoad='" + bedMaxLoad + '\'' +
                ", specification='" + specification + '\'' +
                ", hardness='" + hardness + '\'' +
                ", page=" + page +
                '}';
    }
}
