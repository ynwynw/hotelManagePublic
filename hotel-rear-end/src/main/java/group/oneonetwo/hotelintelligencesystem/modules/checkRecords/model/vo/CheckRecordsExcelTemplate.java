package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * @author 文
 * @TableName check_records
 */
@ApiModel("检查记录的excel导出模板")
public class CheckRecordsExcelTemplate implements Serializable {


    /**
     * 检测人id
     */
    @ExcelProperty(value = "检测人id",index = 0)
    @ApiModelProperty("检测人id")
    private String uId;

    /**
     * 检测类型
     */
    @ExcelProperty(value = "检测类型",index = 1)
    @ApiModelProperty("检测类型")
    private Integer checkType;

    /**
     * 检测结果
     */
    @ExcelProperty(value = "检测结果",index = 2)
    @ApiModelProperty("检测结果")
    private String checkRes;

    /**
     * 检测时间
     */
    @ExcelProperty(value = "检测时间",index = 3)
    @ApiModelProperty("检测时间")
    private String checkTime;


    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getCheckRes() {
        return checkRes;
    }

    public void setCheckRes(String checkRes) {
        this.checkRes = checkRes;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }


}
