package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName materials_apply
 */
@TableName(value ="materials_apply")
public class MaterialsApplyPO implements Serializable {
    /**
     *
     */
    @TableId("id")
    private String id;

    /**
     * 申请人类型
     */
    @TableField("u_type")
    private Integer uType;

    @TableField("apply_thing")
    private String applyThing;

    /**
     * 申请数量
     */
    @TableField("apply_num")
    private Double applyNum;

    /**
     * 申请单位
     */
    @TableField("thing_unit")
    private String thingUnit;

    /**
     * 紧急程度(0:不紧急;1:普通紧急;2:非常紧急)
     */
    @TableField("emergency_level")
    private Integer emergencyLevel;

    /**
     * 申请备注
     */
    @TableField("hotel_id")
    private String hotelId;


    @TableField("apply_remarks")
    private String applyRemarks;

    /**
     * 申请状态(0未审核;1通过;2拒绝)
     */
    @TableField("review_status")
    private Integer reviewStatus;


    @TableField("isolation_id")
    private  String isolationId;

    public String getIsolationId() {
        return isolationId;
    }

    public void setIsolationId(String isolationId) {
        this.isolationId = isolationId;
    }

    /**
     * 审核备注
     */
    @TableField("review_remarks")
    private String reviewRemarks;

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
     */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;


    /**
     * 是否删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getuType() {
        return uType;
    }

    public void setuType(Integer uType) {
        this.uType = uType;
    }


    public String getApplyThing() {
        return applyThing;
    }

    public void setApplyThing(String applyThing) {
        this.applyThing = applyThing;
    }

    public Double getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Double applyNum) {
        this.applyNum = applyNum;
    }

    public String getThingUnit() {
        return thingUnit;
    }

    public void setThingUnit(String thingUnit) {
        this.thingUnit = thingUnit;
    }

    public Integer getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(Integer emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getApplyRemarks() {
        return applyRemarks;
    }

    public void setApplyRemarks(String applyRemarks) {
        this.applyRemarks = applyRemarks;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewRemarks() {
        return reviewRemarks;
    }

    public void setReviewRemarks(String reviewRemarks) {
        this.reviewRemarks = reviewRemarks;
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
}
