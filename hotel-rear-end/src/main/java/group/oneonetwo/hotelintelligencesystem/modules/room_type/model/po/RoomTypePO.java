package group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 文
 */
@TableName("room_type")
public class RoomTypePO implements Serializable {
    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("hotel_id")
    private String hotelId;

    @TableField("bed_type")
    private String bedType;

    @TableField("isolation_fee")
    private Integer isolationFee;

    @TableField("bed")
    private Integer bed;

    @TableField("max_load")
    private Integer maxLoad;

    @TableField("have_window")
    private Integer haveWindow;

    @TableField("network")
    private Integer network;

    @TableField("have_breakfast")
    private Integer haveBreakfast;

    @TableField("media")
    private String media;

    @TableField("rushroom_supporting")
    private String rushroomSupporting;

    @TableField("food_related")
    private String foodRelated;

    @TableField("other_fee")
    private Integer otherFee;

    @TableField("cover")
    private String cover;

    @TableField("fee")
    private Double fee;

    @TableField("sort")
    private Integer sort;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;


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
}
