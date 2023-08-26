package group.oneonetwo.hotelintelligencesystem.modules.room_type.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("房间类型实体")
public interface RoomTypeMapper extends BaseMapper<RoomTypePO> {
    List<RoomTypeVO> currentRoomTypeList(String id, Integer isIsolation);
}
