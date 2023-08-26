package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName check_records
 */
@TableName(value ="check_records")
public class CheckRecordsPO implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private String id;

    /**
     * 检测人id
     */


    @TableField("u_id")
    private String uId;

    /**
     * 检测类型
     */
    @TableField("check_type")
    private Integer checkType;

    /**
     * 检测结果
     */
    @TableField("check_res")
    private Double checkRes;

    /**
     * 检测时间
     */
    @TableField("check_time")
    private Date checkTime;

    /**
     *
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     *
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /**
     *
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     *
     */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     *
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
