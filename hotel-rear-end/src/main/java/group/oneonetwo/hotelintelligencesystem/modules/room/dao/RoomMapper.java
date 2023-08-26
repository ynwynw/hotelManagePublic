package group.oneonetwo.hotelintelligencesystem.modules.room.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.DetailVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface RoomMapper extends BaseMapper<RoomPO> {
    public List<RoomVO> getAllList(@Param("vo") RoomVO roomVO);
    public DetailVO getDetail(String id);
    public  List<RoomVO> getRoomTypeList(@Param("vo") RoomVO roomVO);

    Integer unlockRoom(String id);
}
