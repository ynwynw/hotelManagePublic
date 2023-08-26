package group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.util.Date;

@ApiModel("房间类型实体")
public class RoomTypeVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("房间名称")
    private String name;

    @ApiModelProperty("酒店id")
    private String hotelId;

    @ApiModelProperty("床类型")
    private String bedType;


    @ApiModelProperty("隔离费用")
    private Integer isolationFee;

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
    private Double fee;

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

    private Page page;


    public Integer getIsolationFee() {
        return isolationFee;
    }

    public void setIsolationFee(Integer isolationFee) {
        this.isolationFee = isolationFee;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
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


    @Override
    public String toString() {
        return "RoomTypeVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", bedType='" + bedType + '\'' +
                ", isolationFee=" + isolationFee +
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
                ", page=" + page +
                '}';
    }
}
