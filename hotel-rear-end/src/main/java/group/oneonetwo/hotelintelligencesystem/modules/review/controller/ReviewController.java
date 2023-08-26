package group.oneonetwo.hotelintelligencesystem.modules.review.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.service.ReviewService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liustart
 * @create 2022-04-19 22:24
 */

@RestController
@Api(tags="审批实体")
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @PostMapping("add")
    @ApiOperation("增加审批信息")
    public Reply<ReviewVO> add(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.add(reviewVO));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查审批信息")
    public Reply<ReviewVO> getById(@PathVariable("id") String id) {
        return Reply.success(reviewService.selectOneByIdReturnVO(id));
    }

    @PostMapping("delete/{id}")
    @ApiOperation("删除审批信息")
    public Reply deleteById(@PathVariable("id") String id){
        return reviewService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改审批信息")
    public Reply<ReviewVO> modify(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.save(reviewVO));
    }

    @ApiOperation("查询审批信息列表(分页)")
    @PostMapping("page")
    public Reply<Page<ReviewVO>> getPage(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.getPage(reviewVO));
    }

    @ApiOperation("customer提交审核接口")
    @PostMapping("check")
    public Reply<ReviewVO> getCheck(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.getCheck(reviewVO));
    }

    @ApiOperation("hotel_admin审核提交的信息")
    @PostMapping("reviews")
    public Reply getReviews(@RequestBody ReviewVO reviewVO){
        reviewService.getReviews(reviewVO);
        return  Reply.success();
    }
    @ApiOperation("获取个人申请记录")
    @PostMapping("my")
    public Reply<Page<ReviewVO>> my(@RequestBody ReviewVO reviewVO){
        return  Reply.success(reviewService.my(reviewVO));
    }

    @ApiOperation("支付隔离费用")
    @PostMapping("pay")
    public Reply payDeclaration(String id,String walletPwd) {
        reviewService.payDeclaration(id,walletPwd);
        return Reply.success();
    }

    @ApiOperation("支付宝支付隔离费用")
    @GetMapping("pay/alipay/pre/{id}")
    public Reply payDeclarationForAlipay(@PathVariable("id") String id) throws Exception {
        return Reply.success(reviewService.payDeclarationForAlipay(id));
    }

    @ApiOperation("支付宝确认支付状态")
    @GetMapping("pay/alipay/check/{id}")
    public Reply checkDeclarationPayStatusForAlipay(@PathVariable("id") String id) throws Exception {
        return Reply.success(reviewService.checkDeclarationPayStatusForAlipay(id));
    }








}
