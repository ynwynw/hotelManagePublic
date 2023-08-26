package group.oneonetwo.hotelintelligencesystem.modules.chart.service.impl;

import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.modules.chart.dao.ChartMapper;
import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;
import group.oneonetwo.hotelintelligencesystem.modules.chart.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 文
 */
@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    ChartMapper chartMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public List<ChartVO> orderRoomTypeChart() {
        return chartMapper.orderRoomTypeChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> mapChart() {
        return chartMapper.mapChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> wayChart() {
        return chartMapper.wayChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> orderNumOf7Day() {
        return chartMapper.orderNumOf7Day(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> orderNumOf1Year() {
        return chartMapper.orderNumOf1Year(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> incomeOf7Day() {
        return chartMapper.incomeOf7Day(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> wayOnIsolation(String range, Integer way) {
        String hotelId = null;
        if (!"admin".equals(authUtils.getRole())) {
            hotelId = authUtils.getUserHotelId();
        }
        if ("all".equals(range)) {
            return chartMapper.wayOnIsolation(hotelId);
        }else if ("day".equals(range)){
            return chartMapper.wayOnIsolationOfDay(way,hotelId);
        }else if ("month".equals(range)){
            return null;
//            return chartMapper.wayOnIsolationOfMonth(way,hotelId);
        }else {
            throw new CommonException(Integer.valueOf(ResultCode.NOT_FOUND.getCode()),ResultCode.NOT_FOUND.getMsg());
        }
    }

    @Override
    public List<ChartVO> isolationIncome() {
        return chartMapper.isolationIncome(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> isolationCheckIn() {
        return chartMapper.isolationCheckIn();
    }

    @Override
    public List<ChartVO> typeOnIsolation(String range, Integer type) {
        String hotelId = null;
        if (!"admin".equals(authUtils.getRole())) {
            hotelId = authUtils.getUserHotelId();
        }
        if ("all".equals(range)) {
            return chartMapper.typeOnIsolation(hotelId);
        }else if ("week".equals(range)){
            return chartMapper.typeOnIsolationOfWeek(type,hotelId);
        }else if ("month".equals(range)){
            return chartMapper.typeOnIsolationOfMonth(type,hotelId);
        }else {
            throw new CommonException(Integer.valueOf(ResultCode.NOT_FOUND.getCode()),ResultCode.NOT_FOUND.getMsg());
        }
    }

    @Override
    public List<ChartVO> statusOnIsolation(String range, Integer status) {
        String hotelId = null;
        if (!"admin".equals(authUtils.getRole())) {
            hotelId = authUtils.getUserHotelId();
        }
        if ("all".equals(range)) {
            //需要的可以对照chartMapper.typeOnIsolation来改造
            return null;
//            return chartMapper.statusOnIsolation(hotelId);
        }else if ("week".equals(range)){
            //需要的可以对照chartMapper.typeOnIsolationOfWeek来改造
            return null;
//            return chartMapper.statusOnIsolationOfWeek(status,hotelId);
        }else if ("month".equals(range)){
            if (status == 0) {
                return chartMapper.statusZeroOnIsolationOfMonth(hotelId);
            }else {
                return chartMapper.statusOnIsolationOfMonth(status,hotelId);
            }

        }else {
            throw new CommonException(Integer.valueOf(ResultCode.NOT_FOUND.getCode()),ResultCode.NOT_FOUND.getMsg());
        }
    }

    @Override
    public List<ChartVO> numOnIsolation(String range) {
        String hotelId = null;
        if (!"admin".equals(authUtils.getRole())) {
            hotelId = authUtils.getUserHotelId();
        }
        if ("all".equals(range)) {
            //需要的可以对照chartMapper.typeOnIsolation来改造
            return null;
//            return chartMapper.statusOnIsolation(hotelId);
        }else if ("week".equals(range)){
            return chartMapper.numOnIsolationOfWeek(hotelId);
        }else if ("month".equals(range)){
            //需要的可以对照chartMapper.typeOnIsolationOfMonth来改造
//            return chartMapper.numOnIsolationOfMonth(hotelId);
            return null;
        }else {
            throw new CommonException(Integer.valueOf(ResultCode.NOT_FOUND.getCode()),ResultCode.NOT_FOUND.getMsg());
        }
    }
}
