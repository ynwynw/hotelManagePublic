package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liustart
 * @create 2022-03-27 8:47
 */

@ApiModel("日志实体")
public class LogsVO implements Serializable {

    @ExcelProperty(value = "日志ID",index = 0)
    @ApiModelProperty("主键")
    private String id;

    @ExcelProperty(value = "简讯",index = 1)
    @ApiModelProperty("简讯")
    private  String sketch;

    @ExcelProperty(value = "详情",index = 2)
    @ApiModelProperty("详情")
    private String detail;

    @ExcelProperty(value = "日志类型(0:普通日志，1:数据修改，2:系统修改，3:出错日志，4:警告日志)",index = 3)
    @ApiModelProperty("（0:普通日志，1:数据修改，2:系统修改，3:出错日志，4:警告日志）")
    private Integer type;

    @ExcelProperty(value = "日志权限等级（0:仅管理员，1:酒店管理员和0，2:酒店员工和1",index = 4)
    @ApiModelProperty("日志权限等级（0:仅管理员，1:酒店管理员和0，2:酒店员工和1）")
    private Integer roleLevel;

    @ExcelIgnore
    @ApiModelProperty("酒店id")
    private String hotelId;

    @ExcelProperty(value = "是否删除",index = 5)
    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ExcelProperty(value = "创建人",index = 6)
    @ApiModelProperty("创建人")
    private String createBy;

    @ExcelProperty(value = "创建时间",index = 7)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ExcelIgnore
    private Page page;

    @ExcelIgnore
    private String role;

    @ExcelIgnore
    @ApiModelProperty("起始时间")
    private String beginTime;

    @ExcelIgnore
    @ApiModelProperty("结束时间")
    private String endTime;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    @Override
    public String toString() {
        return "LogsVO{" +
                "id='" + id + '\'' +
                ", sketch='" + sketch + '\'' +
                ", detail='" + detail + '\'' +
                ", type=" + type +
                ", roleLevel=" + roleLevel +
                ", hotelId='" + hotelId + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
