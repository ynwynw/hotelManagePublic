package group.oneonetwo.hotelintelligencesystem.modules.hotel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HotelMapper extends BaseMapper<HotelPO> {

    public Page<HotelVO> getPageWithDistance(@Param("page") Page<HotelVO> page, HotelVO vo);

}
