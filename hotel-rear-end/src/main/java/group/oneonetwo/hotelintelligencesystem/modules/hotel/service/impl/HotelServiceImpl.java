package group.oneonetwo.hotelintelligencesystem.modules.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qiniu.util.Auth;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.dao.HotelMapper;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.PI;

/**
 * @author 文
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HotelServiceImpl implements IHotelService {
    @Autowired
    HotelMapper hotelMapper;

    @Autowired
    IUserService userService;

    @Autowired
    LogsService logsService;

    @Autowired
    AuthUtils authUtils;

    @Override
    public HotelVO add(HotelVO hotelVO){
        if(hotelVO==null){
            throw new SavaException("插入用户失败:酒店实体为空");
        }
        if(!"".equals(hotelVO.getDeptId())&&hotelVO.getDeptId()!=null){
            QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_id",hotelVO.getDeptId());
            List<HotelPO> hotelPOS = hotelMapper.selectList(wrapper);

            if(!hotelPOS.isEmpty()){
                throw new SavaException("该部门已被绑定,绑定的酒店为: "+hotelPOS.get(0).getName());
            }
        }

        HotelPO hotelP0=new HotelPO();
        BeanUtils.copyProperties(hotelVO,hotelP0);
        int insert=hotelMapper.insert(hotelP0);

        Gson gson = new Gson();
//        hotelVO.setOtherPolicy(WStringUtils.removeHtml(hotelVO.getOtherPolicy()));
        logsService.createLog("【添加】酒店信息",gson.toJson(hotelVO),1,1);

        if(insert>0){
            return hotelVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public HotelVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        HotelPO hotelP0=hotelMapper.selectById(id);
        HotelVO hotelVO=new HotelVO();
        if(hotelP0!=null){
            BeanUtils.copyProperties(hotelP0,hotelVO);
        }
        return hotelVO;
    }

    @Override
    public HotelVO save(HotelVO hotelVO){
        HotelPO before = selectOneById(hotelVO.getId());
        if(hotelVO==null){
            throw new CommonException(501,"hotel实体为空");
        }
        if(!"".equals(hotelVO.getDeptId())&&hotelVO.getDeptId()!=null){
            QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_id",hotelVO.getDeptId());
            List<HotelPO> hotelPOS = hotelMapper.selectList(wrapper);

            if(!hotelPOS.isEmpty()){
                if(!hotelPOS.get(0).getId().equals(hotelVO.getId())) {
                    throw new SavaException("该部门已被绑定,绑定的酒店为: " + hotelPOS.get(0).getName());
                }
            }
        }
        HotelVO check=selectOneByIdReturnVO(hotelVO.getId());
        if (check==null){
            throw new CommonException(4004,"找不到id为'"+hotelVO.getId()+"'的数据");
        }
        if (!"admin".equals(authUtils.getRole())) {
            hotelVO.setBadge(null);
        }
        HotelPO hotelPO =new HotelPO();
        BeanUtils.copyProperties(hotelVO, hotelPO);
        int save=hotelMapper.updateById(hotelPO);

        Gson gson = new Gson();
        hotelPO.setOtherPolicy(WStringUtils.removeHtml(hotelPO.getOtherPolicy()));
        logsService.createLog("【修改】酒店信息",gson.toJson(before) + "@*@" + gson.toJson(hotelPO),1,1);

        if(save>0){
            return hotelVO;
        }
        throw  new SavaException("更改酒店失败");
    }
    @Override
    public  Integer deleteById(String id){
        HotelVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为'"+id+"'的数据");

        }
        Gson gson = new Gson();
        check.setOtherPolicy(WStringUtils.removeHtml(check.getOtherPolicy()));
        logsService.createLog("【删除】酒店信息",gson.toJson(check),1,1);
        int i= hotelMapper.deleteById(id);
        return i;
    }

    @Override
    public HotelVO selectOneByDeptId(String deptId) {
        QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id",deptId);
        HotelPO hotelPO = hotelMapper.selectOne(wrapper);
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelPO,hotelVO);
        return hotelVO;
    }

    @Override
    public Page<HotelVO> getPage(HotelVO hotelVO) {
        Page<HotelVO> res = null;
        if (WStringUtils.isBlank(hotelVO.getLatitude()) && WStringUtils.isBlank(hotelVO.getLongitude())) {
            // 构建查询条件
            QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
            if (!"".equals(hotelVO.getName()) && hotelVO.getName() != null) {
                wrapper.like("name",hotelVO.getName());
            }
            if (!"".equals(hotelVO.getAddress()) && hotelVO.getAddress() != null) {
                wrapper.like("address",hotelVO.getAddress());
            }
            if (hotelVO.getAllowIsolation() != null) {
                wrapper.eq("allow_isolation",hotelVO.getAllowIsolation());
            }
            if (!WStringUtils.isBlank(hotelVO.getProvince())) {
                wrapper.eq("province",hotelVO.getProvince());
            }
            if (hotelVO.getCollection() != null) {
                wrapper.in("id",hotelVO.getCollection());
            }
            wrapper.orderByAsc("sort").orderByAsc("id");
            Page<HotelPO> page = new Page<>(hotelVO.getPage().getPage(), hotelVO.getPage().getSize());
            Page<HotelPO> poiPage = (Page<HotelPO>) hotelMapper.selectPage(page, wrapper);
            res = ConvertUtils.transferPage(poiPage,HotelVO.class);
        }else {
            hotelVO = countCoordinateRange(hotelVO);
            Page<HotelVO> page = new Page<>(hotelVO.getPage().getPage(), hotelVO.getPage().getSize());
            res = hotelMapper.getPageWithDistance(page, hotelVO);
        }


        return res;
    }

    @Override
    public HotelVO myHotel() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        UserVO userVO = userService.selectOneByIdReturnVO(uid);
        if (userVO == null) {
            throw new CommonException("暂无信息,请通知管理员绑定酒店");
        }
        QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id",userVO.getDept());
        HotelPO hotelPO = hotelMapper.selectOne(wrapper);
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelPO,hotelVO);
        return hotelVO;
    }

    @Override
    public HotelVO allow(String hotelId ,Integer id) {
        HotelPO hotelPO = selectOneById(hotelId);
        hotelPO.setAllowIsolation(id);
        hotelMapper.updateById(hotelPO);
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelPO,hotelVO);
        return hotelVO;
    }

    @Override
    public HotelPO selectOneById(String id){
        HotelPO hotelPO =hotelMapper.selectById(id);
        return hotelPO;
    }

    private HotelVO countCoordinateRange(HotelVO hotelVO) {
        Double longitude = Double.valueOf(hotelVO.getLongitude());
        Double latitude = Double.valueOf(hotelVO.getLatitude());
        Double radius = Double.valueOf(hotelVO.getDistance());
        if (longitude >= 180
                || longitude <= -180
                || latitude >= 90
                || latitude <= -90) {
            throw new CommonException("【HotelServiceImpl.countCoordinateRange】非法坐标值--longitude:" + longitude + ",latitude:" + latitude);
        }
        Double degree = (24901 * 1609) / 360.0;

        Double radiusLng = (1 / (degree * Math.cos(latitude * (PI / 180)))) * radius;
        hotelVO.setMinLongitude(String.valueOf(longitude-radiusLng));
        hotelVO.setMaxLongitude(String.valueOf(longitude+radiusLng));

        Double radiusLat = (1/degree)*radius;
        hotelVO.setMinLatitude(String.valueOf(latitude-radiusLat));
        hotelVO.setMaxLatitude(String.valueOf(latitude+radiusLat));

        return hotelVO;
    }


}
