package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service.IMaterialsApplyService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ehcache.spi.service.MaintainableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文
 * @description: 物资申请控制层
 * @date 2022/4/22 21:29
 */
@Api(tags="物资申请相关接口")
@RestController
@RequestMapping("api/materialsApply")
public class MaterialsApplyController {

    @Autowired
    IMaterialsApplyService maintainableService;

    @PostMapping("apply")
    @ApiOperation("物资申请")
    public Reply apply(@RequestBody MaterialsApplyVO materialsApplyVO) {
        return Reply.success(maintainableService.apply(materialsApplyVO));
    }

    @PostMapping("page")
    public Reply<Page<MaterialsApplyVO>> getPage(@RequestBody MaterialsApplyVO materialsApplyVO) {
        return Reply.success(maintainableService.getPage(materialsApplyVO));
    }

    @PostMapping("reviewPage")
    public Reply<Page<MaterialsApplyVO>> getReviewPage(@RequestBody MaterialsApplyVO materialsApplyVO) {
        return Reply.success(maintainableService.getReviewPage(materialsApplyVO));
    }



    @PostMapping("add")
    @ApiOperation("物资增加")
    public Reply<MaterialsApplyVO> add(@RequestBody MaterialsApplyVO materialsApplyVO){
        return Reply.success(maintainableService.add(materialsApplyVO));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查物资信息")
    public Reply<MaterialsApplyVO> getById(@PathVariable("id") String id) {
        return Reply.success(maintainableService.selectOneByIdReturnVO(id));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("根据id删除物资信息")
    public Reply deleteById(@PathVariable("id") String id){
        return maintainableService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改物资信息")
    public Reply<MaterialsApplyVO> modify(@RequestBody MaterialsApplyVO materialsApplyVO){
        return Reply.success(maintainableService.save(materialsApplyVO));
    }

    @PostMapping("review")
    @ApiOperation("admin审核")
    public Reply getCheck(@RequestBody MaterialsApplyVO materialsApplyVO){
        maintainableService.review(materialsApplyVO);
        return Reply.success();
    }

}
