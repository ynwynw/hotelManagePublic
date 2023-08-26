package group.oneonetwo.hotelintelligencesystem.modules.discounts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 文
 */
@Api(tags="折扣相关接口")
@RestController
@RequestMapping("api/discounts")
public class DiscountsController {

    @Autowired
    IDiscountsService discountsService;

    @PostMapping("add")
    @ApiOperation("增加折扣")
    public Reply<DiscountsVO> add(@RequestBody DiscountsVO discountsVO){
        return Reply.success(discountsService.add(discountsVO));
    }

    @ApiOperation("删除折扣")
    @GetMapping("delete/{id}")
    public Reply deleteById(@PathVariable("id") String id){
        return discountsService.deleteById(id)>0?Reply.success():Reply.failed();
    }

    @ApiOperation("根据id查找折扣")
    @GetMapping("get/{id}")
    public Reply<DiscountsVO> selectById(@PathVariable("id") String id){
        return Reply.success(discountsService.selectOneByIdReturnVO(id));
    }

    @ApiOperation("更改折扣")
    @PostMapping("modify")
    public Reply<DiscountsVO> modify(@RequestBody DiscountsVO discountsVO){
        return Reply.success(discountsService.saveone(discountsVO));
    }

    @ApiOperation("查询折扣列表(分页)")
    @PostMapping("page")
    public Reply<Page<DiscountsVO>> getPage(@RequestBody DiscountsVO discountsVO){
        return Reply.success(discountsService.getPage(discountsVO));
    }

    @ApiOperation("查询当下酒店优惠券")
    @GetMapping("list/hotel/{id}")
    public Reply<List<DiscountsVO>> getHotelDiscountListByHotelId(@PathVariable("id") String id) {
        return Reply.success(discountsService.getHotelDiscountListByHotelId(id));
    }

    @ApiOperation("查询当下酒店个人优惠券(未领取)")
    @GetMapping("list/personal/{id}")
    public Reply<List<DiscountsVO>> getPersonalDiscountListByHotelId(@PathVariable("id") String id) {
        return Reply.success(discountsService.getPersonalDiscountListByHotelId(id));
    }

    @ApiOperation("查询当前可用优惠券(含是否可使用状态)")
    @PostMapping("list/personal/got")
    public Reply<List<DiscountsVO>> getCurrentCanUseMyPersonalDiscountList(@RequestBody DiscountsVO discountsVO) {
        return Reply.success(discountsService.getCurrentCanUseMyPersonalDiscountList(discountsVO));
    }
}
