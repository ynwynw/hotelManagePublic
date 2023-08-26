package group.oneonetwo.hotelintelligencesystem.modules.room_type.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.dao.RoomTypeMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoomTypeServiceImpl implements IRoomTypeServeice {
    @Autowired
    RoomTypeMapper roomTypeMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    LogsService logsService;

    @Override
    public RoomTypeVO add(RoomTypeVO roomTypeVO){
        if(roomTypeVO==null){
            throw new SavaException("插入用户失败,房的类型实体为空");
        }
        roomTypeVO.setHotelId(authUtils.getUserHotelId());
        RoomTypePO roomTypePO=new RoomTypePO();
        BeanUtils.copyProperties(roomTypeVO,roomTypePO);
        int insert=roomTypeMapper.insert(roomTypePO);
        Gson gson = new Gson();
        logsService.createLog("【添加】部门信息",gson.toJson(roomTypeVO),1,1);
        if(insert>0){
            return roomTypeVO;
        }
        throw  new SavaException("插入用户失败");
    }

    @Override
    public RoomTypeVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数不能为空");
        }
        RoomTypePO roomTypePO=roomTypeMapper.selectById(id);
        RoomTypeVO roomTypeVO=new RoomTypeVO();
        if(roomTypePO!=null){
            BeanUtils.copyProperties(roomTypePO,roomTypeVO);
        }
        return roomTypeVO;
    }
    @Override
    public Integer deleteById(String id){
        RoomTypeVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为"+id+"的数据");
        }
        Gson gson = new Gson();
        logsService.createLog("【删除】房间类型信息",gson.toJson(check),1,1);
        int i=roomTypeMapper.deleteById(id);
        return i;
    }
    @Override
    public RoomTypePO save(RoomTypeVO roomTypeVO){
        if(roomTypeVO==null){
            throw new CommonException(501,"房间的类型实体为空");
        }
        RoomTypeVO check=selectOneByIdReturnVO(roomTypeVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为"+roomTypeVO.getId()+"的数据");
        }
        RoomTypePO roomTypePO=new RoomTypePO();
        BeanUtils.copyProperties(roomTypeVO,roomTypePO);
        int save=roomTypeMapper.updateById(roomTypePO);
        if(save>0){
            return roomTypeMapper.selectById(roomTypePO.getId());
        }
        throw new SavaException("更改房间的类型失败");
    }
    @Override
    public RoomTypePO selectOneById(String id){
        RoomTypePO roomTypePO=roomTypeMapper.selectById(id);
        return roomTypePO;
    }

    @Override
    public RoomTypeVO saveone(RoomTypeVO roomTypeVO){
        RoomTypeVO before = selectOneByIdReturnVO(roomTypeVO.getId());
        RoomTypePO save=save(roomTypeVO);
        BeanUtils.copyProperties(save,roomTypeVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】房间类型信息",gson.toJson(before) + "@*@" + gson.toJson(save),1,1);
        return roomTypeVO;
    }

    @Override
    public Page<RoomTypeVO> getPage(RoomTypeVO roomTypeVO){
        QueryWrapper<RoomTypePO> wrapper=new QueryWrapper<>();
        if(authUtils.getUserHotelId().equals("0")){
            Page<RoomTypePO> page=new Page<>(roomTypeVO.getPage().getPage(),roomTypeVO.getPage().getSize());
            Page<RoomTypePO> poiPage=(Page<RoomTypePO>) roomTypeMapper.selectPage(page,wrapper);
            return ConvertUtils.transferPage(poiPage,RoomTypeVO.class);
        }else {
            wrapper.eq("hotel_id",authUtils.getUserHotelId());
            Page<RoomTypePO> page=new Page<>(roomTypeVO.getPage().getPage(),roomTypeVO.getPage().getSize());
            Page<RoomTypePO> poiPage=(Page<RoomTypePO>) roomTypeMapper.selectPage(page,wrapper);
            return ConvertUtils.transferPage(poiPage,RoomTypeVO.class);
        }

    }

    @Override
    public List<RoomTypeVO> currentRoomTypeList(String id, Integer isIsolation) {
        return roomTypeMapper.currentRoomTypeList(id,isIsolation);
    }
}
