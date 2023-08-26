package group.oneonetwo.hotelintelligencesystem.modules.chart.controller;

import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;
import group.oneonetwo.hotelintelligencesystem.modules.chart.service.IChartService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文
 * @description 可视化图表控制层
 */
@Api(tags="可视化图表相关接口")
@RestController
@RequestMapping("api/chart")
public class ChartController {

    @Autowired
    IChartService chartService;

    @ApiOperation("各房型订单数统计")
    @GetMapping("order/roomType")
    public Reply<List<ChartVO>> orderRoomTypeChart() {
        return Reply.success(chartService.orderRoomTypeChart());
    }

    @ApiOperation("订单来访地统计")
    @GetMapping("order/map")
    public Reply<List<ChartVO>> mapChart() {
        return Reply.success(chartService.mapChart());
    }

    @ApiOperation("订单来源统计")
    @GetMapping("order/way")
    public Reply<List<ChartVO>> wayChart() {
        return Reply.success(chartService.wayChart());
    }

    @ApiOperation("近一周订单数量统计")
    @GetMapping("order/orderNum/day/7")
    public Reply<List<ChartVO>> orderNumOf7Day() {
        return Reply.success(chartService.orderNumOf7Day());
    }

    @ApiOperation("近一年订单数统计")
    @GetMapping("order/orderNum/year/1")
    public Reply<List<ChartVO>> orderNumOf1Year() {
        return Reply.success(chartService.orderNumOf1Year());
    }

    @ApiOperation("近一周营业额统计")
    @GetMapping("order/income/day/7")
    public Reply<List<ChartVO>> incomeOf7Day() {
        return Reply.success(chartService.incomeOf7Day());
    }

    @ApiOperation("隔离人员分配/自申报统计")
    @GetMapping("isolation/way/{range}/{way}")
    public Reply<List<ChartVO>> wayOnIsolation(@PathVariable("range") String range, @PathVariable("way") Integer way) {
        return Reply.success(chartService.wayOnIsolation(range,way));
    }

    @ApiOperation("各地酒店接收隔离人员情况")
    @GetMapping("isolation/receive")
    public Reply<List<ChartVO>> isolationCheckIn() {
        return Reply.success(chartService.isolationCheckIn());
    }

    @ApiOperation("当前酒店接收隔离人员来访地")
    @GetMapping("isolation/income")
    public Reply<List<ChartVO>> isolationIncome() {
        return Reply.success(chartService.isolationIncome());
    }

    @ApiOperation("隔离人员类型统计")
    @GetMapping("isolation/type/{range}/{type}")
    public Reply<List<ChartVO>> typeOnIsolation(@PathVariable("range") String range, @PathVariable("type") Integer type) {
        return Reply.success(chartService.typeOnIsolation(range,type));
    }

    @ApiOperation("隔离人员类型统计")
    @GetMapping("isolation/status/{range}/{status}")
    public Reply<List<ChartVO>> statusOnIsolation(@PathVariable("range") String range, @PathVariable("status") Integer status) {
        return Reply.success(chartService.statusOnIsolation(range,status));
    }

    @ApiOperation("隔离人员数量统计")
    @GetMapping("isolation/num/{range}")
    public Reply<List<ChartVO>> numOnIsolation(@PathVariable("range") String range) {
        return Reply.success(chartService.numOnIsolation(range));
    }


}
