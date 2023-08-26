package group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.vo;

import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 文
 * @TableName order_comment
 */
@ApiModel(value ="订单评论")
public class OrderCommentVO implements Serializable {
    /**
     *
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 酒店id
     */
    @ApiModelProperty("酒店id")
    private String hotelId;

    @ApiModelProperty("订单id")
    private String orderId;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 附件
     */
    @ApiModelProperty("附件")
    private String attachment;

    /**
     * 点赞数
     */
    @ApiModelProperty("点赞数")
    private Integer likes;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 评分
     */
    @ApiModelProperty("评分")
    private Double score;

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

    private Page page;

    private String header;

    private  String roomType;

    private String nickname;

    private String username;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
