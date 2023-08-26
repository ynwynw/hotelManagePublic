package group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 文
 */
@ApiModel("酒店实体")
public class HotelVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("是否允许设置隔离酒店")
    private Integer allowIsolation;

    @ApiModelProperty("部门id")
    private  String deptId;

    @ApiModelProperty("酒店名")
    private String name;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("酒店地址")
    private String address;

    @ApiModelProperty("酒店停车场")
    private String parkingLot;

    @ApiModelProperty("其他政策")
    private String otherPolicy;

    @ApiModelProperty("徽章")
    private String badge;

    @ApiModelProperty("经度")
    private String longitude;

    private String minLongitude;

    private String maxLongitude;

    @ApiModelProperty("纬度")
    private String latitude;

    private String minLatitude;

    private String maxLatitude;

    @ApiModelProperty("距离(输入时为半径条件,输出时为实际距离)")
    private String distance;

    @ApiModelProperty("酒店介绍")
    private String introduce;

    @ApiModelProperty("设施服务")
    private String facilities;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("收藏列表")
    private List<String> collection;

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

    public Integer getAllowIsolation() {
        return allowIsolation;
    }

    public void setAllowIsolation(Integer allowIsolation) {
        this.allowIsolation = allowIsolation;
    }

    private Page page;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(String minLongitude) {
        this.minLongitude = minLongitude;
    }

    public String getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(String maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public String getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(String minLatitude) {
        this.minLatitude = minLatitude;
    }

    public String getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(String maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getOtherPolicy() {
        return otherPolicy;
    }

    public void setOtherPolicy(String otherPolicy) {
        this.otherPolicy = otherPolicy;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public List<String> getCollection() {
        return collection;
    }

    public void setCollection(List<String> collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "HotelVO{" +
                "id='" + id + '\'' +
                ", allowIsolation=" + allowIsolation +
                ", deptId='" + deptId + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", parkingLot='" + parkingLot + '\'' +
                ", otherPolicy='" + otherPolicy + '\'' +
                ", badge='" + badge + '\'' +
                ", longitude='" + longitude + '\'' +
                ", minLongitude='" + minLongitude + '\'' +
                ", maxLongitude='" + maxLongitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", minLatitude='" + minLatitude + '\'' +
                ", maxLatitude='" + maxLatitude + '\'' +
                ", distance='" + distance + '\'' +
                ", introduce='" + introduce + '\'' +
                ", facilities='" + facilities + '\'' +
                ", cover='" + cover + '\'' +
                ", sort=" + sort +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", page=" + page +
                '}';
    }
}
