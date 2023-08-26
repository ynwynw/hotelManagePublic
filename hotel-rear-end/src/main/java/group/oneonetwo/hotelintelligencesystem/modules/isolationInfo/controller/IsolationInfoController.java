package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author liustart
 * @create 2022-04-19 20:00
 */

@RestController
@Api(tags="隔离人员详细信息")
@RequestMapping("api/isolationInfo")
public class IsolationInfoController {

    @Autowired
    IsolationInfoService isolationInfoService;

    @PostMapping("add")
    @ApiOperation("增加隔离人员信息")
    public Reply<IsolationInfoVO> add(@RequestBody IsolationInfoVO hotelVO){
        return Reply.success(isolationInfoService.add(hotelVO));
    }
    @GetMapping("test")
    @ApiOperation("测试")
    public Reply<IsolationInfoVO> test(){
        return Reply.success(isolationInfoService.test());
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查隔离人员信息")
    public Reply<IsolationInfoVO> getById(@PathVariable("id") String id) {
        return Reply.success(isolationInfoService.selectOneByIdReturnVO(id));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除人员信息")
    public Reply deleteById(@PathVariable("id") String id){
        return isolationInfoService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改人员信息")
    public Reply<IsolationInfoVO> modify(@RequestBody IsolationInfoVO isolationInfoVO){
        return Reply.success(isolationInfoService.save(isolationInfoVO));
    }

    @ApiOperation("查询隔离人员列表(分页)")
    @PostMapping("page")
    public Reply<Page<IsolationInfoVO>> getPage(@RequestBody IsolationInfoVO isolationInfoVO) {
        return Reply.success(isolationInfoService.getPage(isolationInfoVO));
    }

    @ApiOperation("查询自己的隔离记录(分页)")
    @PostMapping("my")
    public Reply<Page<IsolationInfoVO>> getOwnPage(@RequestBody IsolationInfoVO isolationInfoVO) {
        return Reply.success(isolationInfoService.getOwnPage(isolationInfoVO));
    }


    @ApiOperation("下载订单记录")
    @PostMapping("download")
    public void downloadOrders(@RequestBody IsolationInfoVO isolationInfoVO, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        long currentTimeMillis = System.currentTimeMillis();
        String exportFileName = "酒店隔离人员信息_" + String.valueOf(currentTimeMillis);
        String fileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
       EasyExcel.write(response.getOutputStream(), IsolationInfoVO.class).sheet("隔离人员信息").doWrite(isolationInfoService.getAllList(isolationInfoVO));
    }

    @ApiOperation("防疫人员直接分配房间")
    @PostMapping("distribution")
    public Reply distribution(@RequestBody  IsolationInfoVO isolationInfoVO){
        isolationInfoService.distribution(isolationInfoVO);
        return Reply.success();

    }

}
