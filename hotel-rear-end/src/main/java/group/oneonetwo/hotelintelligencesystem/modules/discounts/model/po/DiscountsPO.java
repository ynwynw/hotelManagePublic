package group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 文
 */
@TableName("discounts")
public class DiscountsPO implements Serializable {
    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("type")
    private Integer type;

    @TableField("discounts")
    private Double discounts;

    @TableField("discounts_type")
    private Integer discountsType;

    @TableField("effect_type")
    private Integer effectType;

    @TableField("effect_condition")
    private Double effectCondition;

    @TableField("hotel_id")
    private String hotelId;

    @TableField("validity_time")
    private Date validityTime;

    @TableField("exclusive_type")
    private Integer exclusiveType;

    @TableField("exclusive_range")
    private String exclusiveRange;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Double discounts) {
        this.discounts = discounts;
    }

    public Integer getEffectType() {
        return effectType;
    }

    public void setEffectType(Integer effectType) {
        this.effectType = effectType;
    }

    public Double getEffectCondition() {
        return effectCondition;
    }

    public void setEffectCondition(Double effectCondition) {
        this.effectCondition = effectCondition;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;
    }

    public Integer getDiscountsType() {
        return discountsType;
    }

    public void setDiscountsType(Integer discountsType) {
        this.discountsType = discountsType;
    }

    public Integer getExclusiveType() {
        return exclusiveType;
    }

    public void setExclusiveType(Integer exclusiveType) {
        this.exclusiveType = exclusiveType;
    }

    public String getExclusiveRange() {
        return exclusiveRange;
    }

    public void setExclusiveRange(String exclusiveRange) {
        this.exclusiveRange = exclusiveRange;
    }
}
