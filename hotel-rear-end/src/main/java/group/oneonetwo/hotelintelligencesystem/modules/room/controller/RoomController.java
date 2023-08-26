package group.oneonetwo.hotelintelligencesystem.modules.room.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.CheckInVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.DetailVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="房间相关接口")
@RestController
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    AuthUtils authUtils;
    
    
    @Autowired
    IRoomService roomService;


    @PostMapping("add")
    @ApiOperation("增加房间")
    public Reply<RoomVO> add(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.add(roomVO));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除房间")
    public  Reply deleteById(@PathVariable("id") String id){
        return roomService.deleteById(id)>0?Reply.success() :Reply.failed();
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查找房间")
    public Reply<RoomVO> selectById(@PathVariable("id") String id){
        return Reply.success(roomService.selectOneByIdReturnVO(id));
    }

    @PostMapping("modify")
    @ApiOperation("更改房间")
    public Reply<RoomVO> modify(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.saveone(roomVO));
    }


    @PostMapping("page")
    @ApiOperation("获取房间列表(分页)")
    public Reply<Page<RoomVO>> getPage(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.getPage(roomVO));
    }

    @PostMapping("getAllList")
    @ApiOperation("获取根据条件查询出来的列表")
    public Reply<List<RoomVO>> getAllList(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.getAllList(roomVO));
    }

    @GetMapping("getDetail/{id}")
    @ApiOperation("获取房间详情的列表")
    public Reply<DetailVO> getDetail(@PathVariable("id") String id){
        return Reply.success(roomService.getDetail(id));
    }

    @PostMapping("getRoomTypeList")
    @ApiOperation("获取房间类型列表")
    public  Reply<List<RoomVO>> getRoomTypeList(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.getRoomTypeList(roomVO));
    }

    @PostMapping("checkIn")
    @ApiOperation("入    住")
    public Reply checkIn(@RequestBody CheckInVO checkInVO) {
        return Reply.success(roomService.checkIn(checkInVO));
    }

    @GetMapping("checkOut/{id}")
    @ApiOperation("退房")
    public Reply checkOut(@PathVariable("id") String id) {
        return Reply.success(roomService.checkOut(id));
    }

    @ApiOperation("获取楼层列表")
    @GetMapping("floor")
    public Reply getFloor() {
        return Reply.success(roomService.getFloor());
    }


    @GetMapping("checkInfo/{id}")
    @ApiOperation("入 住 的人是谁")
    public Reply<IsolationInfoVO> checkInfo(@PathVariable("id") String id) {
        return Reply.success(roomService.checkInfo(id));
    }


    @ApiOperation("隔离人员更换房间")
    @PostMapping("changeRoom")
    public Reply changeRoom(String currentRoomId ,String newRoomId){
        roomService.changeRoom(currentRoomId,newRoomId);
        return Reply.success();
    }

    @ApiOperation("隔离人员的退房")
    @PostMapping("isolationCheckOut")
    public Reply isolationCheckOut(Integer status,String roomId){
        roomService.isolationCheckOut(status,roomId);
        return Reply.success();
    }

    @ApiOperation(("清理消毒房间"))
    @GetMapping("cleanRoom/{roomId}")
    public Reply cleanRoom(@PathVariable("roomId")String roomId){
        roomService.cleanRoom(roomId);
        return Reply.success();
    }


}

