package group.oneonetwo.hotelintelligencesystem.modules.chart.service;

import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;

import java.util.List;

/**
 * @author 文
 */
public interface IChartService {


    List<ChartVO> orderRoomTypeChart();

    List<ChartVO> mapChart();

    List<ChartVO> wayChart();

    List<ChartVO> orderNumOf7Day();

    List<ChartVO> orderNumOf1Year();

    List<ChartVO> incomeOf7Day();

    List<ChartVO> wayOnIsolation(String range, Integer way);

    List<ChartVO> isolationIncome();

    List<ChartVO> isolationCheckIn();

    List<ChartVO> typeOnIsolation(String range, Integer type);

    List<ChartVO> statusOnIsolation(String range, Integer status);

    List<ChartVO> numOnIsolation(String range);
}
