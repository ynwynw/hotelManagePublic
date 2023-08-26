package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo.LogsVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author liustart
 * @create 2022-03-27 8:54
 */

@Api(tags="日志相关接口")
@RestController
@RequestMapping("api/logs")
public class LogsController {
    @Autowired
    LogsService logsService;


//    @PostMapping("add")
//    @ApiOperation("添加日志")
//    public Reply<LogsVO> add(@RequestBody LogsVO logsVO ){
//        return Reply.success(logsService.add(logsVO));
//    }
//
//    @GetMapping("delete/{id}")
//    @ApiOperation("根据id删除日志")
//    public Reply deleteById(@PathVariable("id") String id){
//        return logsService.deleteById(id)>0? Reply.success() :Reply.failed();
//    }
//
    @ApiOperation("根据id查找日志")
    @GetMapping("get/{id}")
    public Reply<LogsVO> selectById(@PathVariable("id")String id){
        return Reply.success(logsService.findById(id));
    }
//
//    @ApiOperation("更改日志")
//    @PostMapping("modify")
//    public Reply<LogsVO> modify(@RequestBody LogsVO logsVO){
//        return Reply.success(logsService.saveone(logsVO));
//    }

    @ApiOperation("获取日志列表(分页)")
    @PostMapping("page")
    public Reply<Page<LogsVO>> getPage(@RequestBody LogsVO vo) {
        return Reply.success(logsService.getPage(vo));
    }


    @ApiOperation("下载日志记录")
    @PostMapping("download")
    public  void downLoadLogs(@RequestBody LogsVO logsVO, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setContentType("utf-8");
        long currentTimeMillis = System.currentTimeMillis();
        String exportFileName = "日志_" + String.valueOf(currentTimeMillis);
        String fileName = URLEncoder.encode(exportFileName, "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),LogsVO.class).sheet("日志").doWrite(logsService.getAllList(logsVO));
    }



}
