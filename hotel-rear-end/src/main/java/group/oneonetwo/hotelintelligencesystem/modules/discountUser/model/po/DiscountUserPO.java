package group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName discount_user
 */
@TableName(value ="discount_user")
public class DiscountUserPO implements Serializable {
    /**
     *
     */
    @TableId
    private String id;

    /**
     *
     */
    @TableField("d_id")
    private String dId;

    /**
     *
     */
    @TableField("uid")
    private String uid;

    /**
     *
     */
    @TableField("is_used")
    private Integer isUsed;

    /**
     *
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     *
     */
    @TableField("create_by")
    private String createBy;

    /**
     *
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *
     */
    @TableField("update_by")
    private String updateBy;

    /**
     *
     */
    @TableField("update_time")
    private Date updateTime;

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

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
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
