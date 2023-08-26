package group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo;

import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("菜单实体")
public class MenuVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("菜单名")
    private String name;

    @ApiModelProperty("父id")
    private String pId;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("图标")
    private  String icon;

    @ApiModelProperty("是否显示(0:没有,1:显示)")
    private String visible;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    private String deptId;

    private String role;

    private List<MenuVO> children;

    private Page page;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"pId\":\"")
                .append(pId).append('\"');
        sb.append(",\"sort\":\"")
                .append(sort).append('\"');
        sb.append(",\"path\":\"")
                .append(path).append('\"');
        sb.append(",\"visible\":\"")
                .append(visible).append('\"');
        sb.append(",\"remark\":\"")
                .append(remark).append('\"');
        sb.append(",\"icon\":")
                .append(icon);
        sb.append(",\"level\":")
                .append(level);
        sb.append(",\"isDeleted\":")
                .append(isDeleted);
        sb.append(",\"createBy\":\"")
                .append(createBy).append('\"');
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"updateBy\":\"")
                .append(updateBy).append('\"');
        sb.append(",\"updateTime\":\"")
                .append(updateTime).append('\"');
        sb.append(",\"deptId\":\"")
                .append(deptId).append('\"');
        sb.append(",\"children\":")
                .append(children);
        sb.append('}');
        return sb.toString();
    }
}
