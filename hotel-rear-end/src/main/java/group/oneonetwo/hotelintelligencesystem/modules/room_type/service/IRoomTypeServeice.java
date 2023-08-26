package group.oneonetwo.hotelintelligencesystem.modules.room_type.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;

import java.util.List;

public interface IRoomTypeServeice {
    RoomTypePO selectOneById(String id);

    RoomTypeVO add(RoomTypeVO roomTypeVO);

    RoomTypePO save(RoomTypeVO roomTypeVO);

    Integer deleteById(String id);

    RoomTypeVO selectOneByIdReturnVO(String id);

    RoomTypeVO saveone(RoomTypeVO roomTypeVO);

    Page<RoomTypeVO> getPage(RoomTypeVO roomTypeVO);

    List<RoomTypeVO> currentRoomTypeList(String id, Integer isIsolation);
}
