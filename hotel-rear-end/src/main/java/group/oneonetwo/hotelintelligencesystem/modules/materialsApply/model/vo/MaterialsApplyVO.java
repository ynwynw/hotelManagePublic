package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName materials_apply
 */
@ApiModel("物质申请的类型实体")
public class MaterialsApplyVO implements Serializable {
    /**
     *
     */
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("起始时间")
    private String beginTime;

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

    @ApiModelProperty("结束时间")
    private String endTime;


    /**
     * 申请人类型
     */
    @ApiModelProperty("hotelId")
    private String hotelId;

    @ApiModelProperty("申请人类型")
    private Integer uType;

    /**
     * 申请人
     */

    /**
     * 申请物品
     */
    @ApiModelProperty("申请物品")
    private String applyThing;

    /**
     * 申请数量
     */
    @ApiModelProperty("申请数量")
    private Double applyNum;

    /**
     * 申请单位
     */
    @ApiModelProperty("申请单位")
    private String thingUnit;

    /**
     * 紧急程度(0:不紧急;1:普通紧急;2:非常紧急)
     */
    @ApiModelProperty("紧急程度")
    private Integer emergencyLevel;

    /**
     * 申请备注
     */
    @ApiModelProperty("申请备注")
    private String applyRemarks;

    /**
     * 申请状态(0未审核;1通过;2拒绝)
     */
    @ApiModelProperty("申请状态")
    private Integer reviewStatus;

    /**
     * 审核备注
     */
    @ApiModelProperty("审核备注")
    private String reviewRemarks;

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



    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Integer isDeleted;


    @ApiModelProperty("隔离id")
    private String isolationId;

    public String getIsolationId() {
        return isolationId;
    }

    public void setIsolationId(String isolationId) {
        this.isolationId = isolationId;
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
