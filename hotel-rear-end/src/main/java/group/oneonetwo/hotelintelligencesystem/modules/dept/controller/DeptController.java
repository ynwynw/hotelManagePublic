package group.oneonetwo.hotelintelligencesystem.modules.dept.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 文
 * @description dept操作类
 */
@Api(tags = "部门相关接口")
@RestController
@RequestMapping("api/dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    @PostMapping("batchAdd")
    @ApiOperation("批量插入")
    public Reply batchAdd(@RequestBody List<DeptVO> deptVOS){
        return deptService.batchAdd(deptVOS);
    }

    @PostMapping("list")
    @ApiOperation("获取部门列表")
    public Reply<List<DeptVO>> getList(@RequestBody DeptVO deptVO) {
        return Reply.success(deptService.getList(deptVO));
    }

    @PostMapping("add")
    @ApiOperation("增加部门")
    public Reply<DeptVO> add(@RequestBody DeptVO deptVO) {
        return Reply.success(deptService.insertOne(deptVO));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除部门")
    public Reply deleteById(@PathVariable("id") String id) {
        return deptService.deleteById(id) > 0 ? Reply.success() : Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改部门")
    public Reply<DeptVO> modify(@RequestBody DeptVO deptVO) {
        return Reply.success(deptService.saveOne(deptVO));
    }

    @ApiOperation("获取非树列表")
    @PostMapping("page")
    public Reply<Page<DeptVO>> getPage(@RequestBody DeptVO vo) {
        return Reply.success(deptService.getPage(vo));
    }


}
