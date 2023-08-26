package group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
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
public class DiscountUserAndDiscountsVO extends DiscountsVO implements Serializable  {
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
    private String createBy;

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

    @Override
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
