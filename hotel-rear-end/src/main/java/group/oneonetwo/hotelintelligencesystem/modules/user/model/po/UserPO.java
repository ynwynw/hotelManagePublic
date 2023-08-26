package group.oneonetwo.hotelintelligencesystem.modules.user.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 文
 * @description user实体
 */
@TableName("user")
public class UserPO implements Serializable {

    @TableId("id")
    private String id;

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("heads")
    private String heads;

    @TableField("phone")
    private String phone;

    @TableField("dept")
    private String dept;

    @TableField("password")
    private String password;

    @TableField("description")
    private String description;

    @TableField("review_status")
    private Integer reviewStatus;

    @TableField("chat_online")
    private String chatOnline;

    @TableField("email")
    private String email;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;



    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChatOnline() {
        return chatOnline;
    }

    public void setChatOnline(String chatOnline) {
        this.chatOnline = chatOnline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
