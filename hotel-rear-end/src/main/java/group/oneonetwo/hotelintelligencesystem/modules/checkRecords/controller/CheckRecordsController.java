package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.controller;

import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import group.oneonetwo.hotelintelligencesystem.listener.CheckRecordsExcelListener;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsExcelTemplate;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsVO;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.service.ICheckRecordsService;

import group.oneonetwo.hotelintelligencesystem.tools.FileUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 文
 * @description: 检测记录控制层
 * @date 2022/4/19 15:48
 */
@Api(tags="检测记录相关接口")
@RestController
@RequestMapping("api/checkRecords")
public class CheckRecordsController {

    @Autowired
    ICheckRecordsService checkRecordsService;

    @PostMapping("add")
    @ApiOperation("增加每日测量")
    public Reply<CheckRecordsVO> add(@RequestBody CheckRecordsVO checkRecordsVO){
        return Reply.success(checkRecordsService.add(checkRecordsVO));
    }


    @GetMapping("delete/{id}")
    @ApiOperation("根据id删除检测记录")
    public Reply deleteById(@PathVariable("id") String id) {
        return checkRecordsService.deleteById(id) > 0 ? Reply.success() : Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改检测结果")
    public Reply<CheckRecordsVO> modify(@RequestBody CheckRecordsVO checkRecordsVO) {
        return Reply.success(checkRecordsService.save(checkRecordsVO));
    }

    @ApiOperation("查询检测列表(分页)")
    @PostMapping("page")
    public Reply<Page<CheckRecordsVO>> getPages(@RequestBody CheckRecordsVO checkRecordsVO) {
        return Reply.success(checkRecordsService.getPages(checkRecordsVO));
    }

    @ApiOperation("查询自己的检测列表(分页)")
    @PostMapping("ownPage")
    public Reply<Page<CheckRecordsVO>> getOwnPage(@RequestBody CheckRecordsVO checkRecordsVO) {
        return Reply.success(checkRecordsService.getOwnPage(checkRecordsVO));
    }


    @ApiOperation("手动插入")
    @PostMapping("manually")
    public Reply insertManually(@RequestBody CheckRecordsVO checkRecordsVO) {
        return checkRecordsService.insertManually(checkRecordsVO) == 1 ? Reply.success() : Reply.failed();
    }

    @ApiOperation("批量导入")
    @PostMapping("upload")
    public Reply upload(MultipartFile file) throws Exception {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        List<CheckRecordsVO> res = EasyExcel.read(FileUtils.multipartFileToFile(file), CheckRecordsExcelTemplate.class, new CheckRecordsExcelListener(checkRecordsService)).sheet().doReadSync();
        return Reply.success("成功导入" + res.size() + "条数据!");
    }

    @ApiOperation("下载模板")
    @PostMapping("template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        long currentTimeMillis = System.currentTimeMillis();
        String exportFileName = "每日检测模板_" + String.valueOf(currentTimeMillis);
        String fileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CheckRecordsExcelTemplate.class).sheet("订单").doWrite(new ArrayList<CheckRecordsVO>());
    }





}
