package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo;


import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName check_records
 */
@ApiModel("检查记录的类型实体")
public class CheckRecordsVO implements Serializable {
    /**
     *
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 检测人id
     */
    @ApiModelProperty("检测人id")
    private String uId;


    @ApiModelProperty("开始时间")
    private Date beginTime;

    @ApiModelProperty("结束时间")
    private Date endTime;


    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("身份证")
    private String idCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 检测类型
     */
    @ApiModelProperty("检测类型")
    private Integer checkType;

    /**
     * 检测结果
     */
    @ApiModelProperty("检测结果")
    private Double checkRes;

    /**
     * 检测时间
     *
     *
     */
    @ApiModelProperty("检测时间")
    private Date checkTime;


    private Page page;



    /**
     *
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     *
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty("is_deleted")
    private Integer isDeleted;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    /**
     *
     */
    public String getId() {
        return id;
    }

    /**
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Double getCheckRes() {
        return checkRes;
    }

    public void setCheckRes(Double checkRes) {
        this.checkRes = checkRes;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
