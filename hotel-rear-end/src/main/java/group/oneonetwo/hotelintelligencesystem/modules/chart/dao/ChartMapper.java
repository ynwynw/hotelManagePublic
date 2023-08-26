package group.oneonetwo.hotelintelligencesystem.modules.chart.dao;

import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 文
 */
@Mapper
public interface ChartMapper {
    List<ChartVO> orderRoomTypeChart(String hotelId);

    List<ChartVO> mapChart(String hotelId);

    List<ChartVO> wayChart(String hotelId);

    List<ChartVO> orderNumOf7Day(String hotelId);

    List<ChartVO> orderNumOf1Year(String hotelId);

    List<ChartVO> incomeOf7Day(String hotelId);

    List<ChartVO> wayOnIsolationOfDay(Integer way, String hotelId);

    List<ChartVO> isolationCheckIn();

    List<ChartVO> isolationIncome(String hotelId);

    List<ChartVO> wayOnIsolation(String hotelId);

    List<ChartVO> typeOnIsolation(String hotelId);

    List<ChartVO> typeOnIsolationOfWeek(Integer type, String hotelId);

    List<ChartVO> typeOnIsolationOfMonth(Integer type, String hotelId);

    List<ChartVO> statusOnIsolationOfMonth(Integer status, String hotelId);

    List<ChartVO> numOnIsolationOfWeek(String hotelId);

    List<ChartVO> statusZeroOnIsolationOfMonth(String hotelId);
}
