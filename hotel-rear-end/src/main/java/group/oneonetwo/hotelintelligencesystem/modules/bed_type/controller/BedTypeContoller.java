package group.oneonetwo.hotelintelligencesystem.modules.bed_type.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.service.IBedTypeService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags="床的类型相关接口")
@RestController
@RequestMapping("api/bedType")
public class BedTypeContoller {
    @Autowired
    IBedTypeService bedTypeService;

    @PostMapping("add")
    @ApiOperation("添加床的类型")
    public Reply<BedTypeVO> add(@RequestBody BedTypeVO bedTypeVO ){
        return Reply.success(bedTypeService.add(bedTypeVO));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除床的类型")
    public Reply deleteById(@PathVariable("id") String id){
        return bedTypeService.deleteById(id)>0? Reply.success() :Reply.failed();
    }

    @ApiOperation("根据id查找床的类型")
    @GetMapping("get/{id}")
    public Reply<BedTypeVO> selectById(@PathVariable("id")String id){
        return Reply.success(bedTypeService.selectOneByIdReturnVO(id));
    }

    @ApiOperation("更改床的类型")
    @PostMapping("modify")
    public Reply<BedTypeVO> modify(@RequestBody BedTypeVO bedTypeVO){
        return Reply.success(bedTypeService.saveone(bedTypeVO));
    }

    @ApiOperation("获取床的类型(分页)")
    @PostMapping("page")
    public Reply<Page<BedTypeVO>> getPage(@RequestBody BedTypeVO bedTypeVO){
        return (Reply<Page<BedTypeVO>>) Reply.success(bedTypeService.getPage(bedTypeVO));
    }

}
