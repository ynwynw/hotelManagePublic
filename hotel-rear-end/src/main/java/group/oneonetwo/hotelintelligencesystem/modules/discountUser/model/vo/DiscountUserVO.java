package group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName discount_user
 */
@ApiModel("折扣-用户表实体")
public class DiscountUserVO implements Serializable {
    /**
     *
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     *
     */
    @ApiModelProperty("折扣id")
    private String dId;

    /**
     *
     */
    @ApiModelProperty("用户id")
    private String uid;

    /**
     *
     */
    @ApiModelProperty("是否使用")
    private Integer isUsed;

    /**
     *
     */
    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    /**
     *
     */
    @ApiModelProperty("创建人")
    private String create_by;

    /**
     *
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty("更新时间")
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

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
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
