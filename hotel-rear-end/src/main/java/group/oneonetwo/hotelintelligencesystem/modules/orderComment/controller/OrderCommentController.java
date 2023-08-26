package group.oneonetwo.hotelintelligencesystem.modules.orderComment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.vo.OrderCommentVO;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.service.IOrderCommentService;

import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文
 * @description: 订单评论控制层
 * @date 2022/5/18 14:43
 */
@Api(tags="订单评论相关接口")
@RestController
@RequestMapping("/api/orderComment")
public class OrderCommentController {

    @Autowired
    IOrderCommentService orderCommentService;

    @ApiOperation("写评论")
    @PostMapping("write")
    public Reply writeComment(@RequestBody OrderCommentVO orderCommentVO) {
        orderCommentService.writeComment(orderCommentVO);
        return Reply.success("评论成功");
    }

    @ApiOperation("获取指定酒店评价平均分(前30条评论)")
    @GetMapping("score/{hotelId}")
    public Reply getAvgScore(@PathVariable("hotelId") String hotelId) {
        return Reply.success(orderCommentService.getAvgScore(hotelId));
    }

    @ApiOperation("获取评论分页数据")
    @PostMapping("page")
    public Reply<Page<OrderCommentVO>> getPage(@RequestBody OrderCommentVO orderCommentVO) {
        return Reply.success(orderCommentService.getPage(orderCommentVO));}



}
