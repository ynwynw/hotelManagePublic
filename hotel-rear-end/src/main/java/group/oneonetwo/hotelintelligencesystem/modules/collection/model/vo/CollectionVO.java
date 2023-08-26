package group.oneonetwo.hotelintelligencesystem.modules.collection.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName collection
 */
@TableName(value ="collection")
public class CollectionVO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 酒店id
     */
    @ApiModelProperty("酒店id")
    private String hotelId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String uid;

    /**
     * 是否收藏(0:否,1:是)
     */
    @ApiModelProperty("是否收藏(0:否,1:是)")
    private Integer status;

    /**
     *
     */
    @ApiModelProperty("is_deleted")
    private Integer isDeleted;

    /**
     *
     */
    @ApiModelProperty("create_by")
    private String createBy;

    /**
     *
     */
    @ApiModelProperty("create_time")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty("update_by")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty("update_time")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
