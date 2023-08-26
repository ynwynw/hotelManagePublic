package group.oneonetwo.hotelintelligencesystem.modules.room_type.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 文
 */
@Api(tags = "房间类型相关接口")
@RestController
@RequestMapping("api/roomType")
public class RoomTypeController {
    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @PostMapping("add")
    @ApiOperation("增加房间的类型")
    public Reply<RoomTypeVO> add(@RequestBody RoomTypeVO roomTypeVO){

        return Reply.success(roomTypeServeice.add(roomTypeVO));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除房间的类型")
    public Reply deleteById(@PathVariable("id") String id){
        return roomTypeServeice.deleteById(id)>0 ?Reply.success():Reply.failed();
    }

    @GetMapping("根据id查找房的类型")
    @ApiOperation("get/{id}")
    public Reply<RoomTypeVO> selectById(@PathVariable("id") String id){
        return Reply.success(roomTypeServeice.selectOneByIdReturnVO(id));
    }


    @PostMapping("modify")
    @ApiOperation("更改房间类型")
    public  Reply<RoomTypeVO> modify(@RequestBody RoomTypeVO roomTypeVO)
    {
        return Reply.success(roomTypeServeice.saveone(roomTypeVO));
    }

    @PostMapping("page")
    @ApiOperation("获取房间类型列表(分页)")
    public Reply<Page<RoomTypeVO>> getPage(@RequestBody RoomTypeVO roomTypeVO){
        return Reply.success(roomTypeServeice.getPage(roomTypeVO));
    }

    @GetMapping("currentRoomTypeList/{id}")
    @ApiOperation("获取当前酒店现有房间类型列表")
    public  Reply<List<RoomTypeVO>> currentRoomTypeList(@PathVariable("id") String id,@Param("isIsolation") Integer isIsolation){
        return Reply.success(roomTypeServeice.currentRoomTypeList(id,isIsolation));
    }
}
