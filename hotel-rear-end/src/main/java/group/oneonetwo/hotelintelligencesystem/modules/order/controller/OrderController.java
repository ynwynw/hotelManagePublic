package group.oneonetwo.hotelintelligencesystem.modules.order.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags="订单相关接口")
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    IOrderService orderService;


//    @PostMapping("add")
    @ApiOperation("添加订单")
    public  Reply<OrderVO> add(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.addOne(orderVO));
    }


    @GetMapping("delete/{id}")
    @ApiOperation("根据id删除订单")
    public Reply deleteById(@PathVariable("id") String id){
        return orderService.deleteById(id)>0?Reply.success() :Reply.failed();
    }


    @ApiOperation("根据id查找订单")
    @GetMapping("get/{id}")
    public  Reply<OrderVO> selectById(@PathVariable("id") String id){
        return Reply.success(orderService.selectOneByIdReturnVO(id));
    }


    @ApiOperation("更改订单")
    @PostMapping("modify")
    public Reply<OrderVO> modify(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.saveone(orderVO));
    }

    @ApiOperation("获取订单列表(分页)")
    @PostMapping("page")
    public Reply<Page<OrderVO>> getPage(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.getPage(orderVO));
    }

    @ApiOperation("下载订单记录")
    @PostMapping("download")
    public void downloadOrders(@RequestBody OrderVO orderVO, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        long currentTimeMillis = System.currentTimeMillis();
        String exportFileName = "酒店订单_" + String.valueOf(currentTimeMillis);
        String fileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OrderVO.class).sheet("订单").doWrite(orderService.getAllList(orderVO));
    }

    @ApiOperation("创建订单")
    @PostMapping("create")
    public Reply<OrderVO> createOrder(@RequestBody OrderVO orderVO) {
        return Reply.success(orderService.createNewOrder(orderVO));
    }

    @ApiOperation("支付订单")
    @PostMapping("payOrder")
    public Reply payOrder(String orderId,String walletPwd) {
        orderService.payOrder(orderId,walletPwd);
        return Reply.success();
    }

    //    暂时注释支付宝相关代码
    /**
     * 通过传入订单号,当orders表中payWay字段为空时,创建该订单的支付宝付款二维码
     * 扫码付款成功后,可以通过异步让支付宝官方通过POST请求通知我们(这里懒得跟前端做websocket连接,所以暂时抛弃这个做法)
     * 也可以通过手动调用查询接口(checkPayOrderForAlipay)去进行该订单交易状态查询(在这也称确定支付)
     * @return
     * @throws Exception
     */
    @ApiOperation("支付宝支付订单")
    @GetMapping("payOrder/alipay/pre/{orderId}")
    public Reply prePayOrderForAlipay(@PathVariable("orderId") String orderId) throws Exception {
        return Reply.success(orderService.prePayOrderForAlipay(orderId));
    }

    /**
     * 支付宝查询订单状态(确认支付)
     * 当调用这个接口时,若为交易成功,且orders表中payWay字段为空(防止重复分配房间),则标记订单状态为已支付并分配房间
     * 若为其他,直接抛出异常
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation("支付宝确认支付")
    @GetMapping("payOrder/alipay/check/{orderId}")
    public Reply checkPayOrderForAlipay(@PathVariable("orderId") String orderId) throws Exception {
        return Reply.success(orderService.checkPayOrderForAlipay(orderId));
    }

    /**
     * 支付宝退款操作,
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation("支付宝退款")
    @GetMapping("cancel/alipay/{orderId}")
    public Reply cancelOrderForAlipay(@PathVariable("orderId") String orderId) throws Exception {
        return Reply.success(orderService.cancelOrderForAlipay(orderId));
    }

    @ApiOperation("取消订单")
    @GetMapping("cancel/{id}")
    public Reply<String> cancelOrder(@PathVariable("id") String id) {
        return Reply.success(orderService.cancelOrder(id));
    }

    @ApiOperation("随机订单")
    @GetMapping("randomOrder")
    public void s() {
        orderService.randomOrder();
    }

    @ApiOperation("获取我的订单")
    @PostMapping("my")
    public  Reply<Page<OrderVO>> my(@RequestBody OrderVO orderVO){

        return Reply.success(orderService.my(orderVO));
    }

    @ApiOperation("获取订单状态次数")
    @PostMapping("count")
    public  Reply<List<OrderVO>> count(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.count(orderVO));
    }
}
