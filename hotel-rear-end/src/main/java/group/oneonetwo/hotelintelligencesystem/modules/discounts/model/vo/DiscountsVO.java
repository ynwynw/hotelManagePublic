package group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 文
 */
@ApiModel("折扣实体")
public class DiscountsVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("折扣名称")
    private String name;

    @ApiModelProperty("折扣描述")
    private String  description;

    @ApiModelProperty("折扣类型(0:全局个人折扣;1:酒店内个人折扣;)")
    private Integer type;

    @ApiModelProperty("折扣幅度")
    private Double discounts;

    @ApiModelProperty("折扣方式(0:立减;1:折扣)")
    private Integer discountsType;

    @ApiModelProperty("生效条件类型(0:满足xx天,1:满减;2:无门槛)")
    private Integer effectType;

    @ApiModelProperty("生效条件(天数)")
    private Double effectCondition;

    @ApiModelProperty("酒店id")
    private String hotelId;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

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

    @ApiModelProperty("有效期")
    private Date validityTime;

    @ApiModelProperty("互斥性(0:不互斥;1:与所有优惠券互斥;2与指定优惠券互斥)")
    private Integer exclusiveType;

    @ApiModelProperty("互斥范围")
    private String exclusiveRange;

    private String bookDay;

    private String orderFee;

    private String currentUse;

    private Boolean canUse;

    private String cantUseReason;

    private Boolean isGot;

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public String getCurrentUse() {
        return currentUse;
    }

    public void setCurrentUse(String currentUse) {
        this.currentUse = currentUse;
    }

    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }

    public String getCantUseReason() {
        return cantUseReason;
    }

    public void setCantUseReason(String cantUseReason) {
        this.cantUseReason = cantUseReason;
    }

    public String getBookDay() {
        return bookDay;
    }

    public void setBookDay(String bookDay) {
        this.bookDay = bookDay;
    }

    public String getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(String orderFee) {
        this.orderFee = orderFee;
    }

    public Boolean getIsGot() {
        return isGot;
    }

    public void setIsGot(Boolean isGot) {
        this.isGot = isGot;
    }

    @Override
    public String toString() {
        return "DiscountsVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", discounts='" + discounts + '\'' +
                ", effectType=" + effectType +
                ", effectCondition=" + effectCondition +
                ", hotelId='" + hotelId + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", page=" + page +
                '}';
    }
}
