package group.oneonetwo.hotelintelligencesystem.components.security.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.dao.DeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.user.dao.UserMapper;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author 文
 */
@Component
public class AuthUtils {

    @Autowired
    IHotelService hotelService;

    @Autowired
    IUserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IDeptService deptService;

    @Autowired
    DeptMapper deptMapper;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户id
     * @return
     */
    public String getUid() {
        String uid = getAuthentication().getName();
        return uid;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public UserVO getUserInfo() {
        return userService.selectOneByIdReturnVO(getUid());
    }

    /**
     * 获取当前用户权限标识
     * @return
     */
    public String getRole() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String authority = null;
        if (iterator.hasNext()) {
            authority = iterator.next().getAuthority();
        }
        if (authority == null) {
            throw new CommonException(401,"无权限");
        }
        return authority;
    }

    /**
     * 获取当前用户所绑定的酒店id
     * @return
     */
    public String getUserHotelId() {
        String role = getRole();
        HotelVO vo = new HotelVO();
        switch (role) {
            case "admin":
                vo.setId("0");
                break;
            case "hotel_admin":
                vo = hotelService.selectOneByDeptId(getUserInfo().getDept());
                if (vo == null) {
                    throw new CommonException("该用户没有绑定酒店");
                }
                break;
            case "hotel_member":
                DeptVO deptVO = deptService.selectOneByIdReturnVO(getUserInfo().getDept());
                if (deptVO == null) {
                    throw new CommonException("部门层级数据异常");
                }
                while(!"0".equals(deptVO.getpId())) {
                    deptVO = deptService.selectOneByIdReturnVO(deptVO.getpId());
                }
                vo = hotelService.selectOneByDeptId(deptVO.getId());
                if (vo == null) {
                    throw new CommonException("该用户没有绑定酒店");
                }
                break;
            default:
                vo.setId("-1");
        }
        return vo.getId();
    }

    /**
     * 获取酒店管理员及二级成员(若要获取所有,可通过修改代码实现)
     * @return
     */
    public List<String> getHotelAllUser() {
        List<String> ids = getHotelAllUser(getUserHotelId());
        return ids;
    }

    public List<String> getHotelAllUser(String hotelId) {
        List<String> ids = getHotelAllDept(hotelId);
        QueryWrapper<UserPO> userPOQueryWrapper = new QueryWrapper<>();
        userPOQueryWrapper.in("dept",ids).select("id");
        List<UserPO> userPOS = userMapper.selectList(userPOQueryWrapper);
        Iterator<UserPO> userPOIterator = userPOS.iterator();
        ids = new ArrayList<>();
        while (userPOIterator.hasNext()){
            ids.add(userPOIterator.next().getId());
        }
        return ids;
    }

    public List<String> getHotelAllDept(String hotelId) {
        HotelVO hotelVO = hotelService.selectOneByIdReturnVO(hotelId);
        List<String> ids = new ArrayList<>();
        ids.add(hotelVO.getDeptId());
        DeptVO deptVO = deptService.selectOneByIdReturnVO(hotelVO.getDeptId());
        QueryWrapper<DeptPO> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id",deptVO.getId()).select("id");
        List<DeptPO> pos = deptMapper.selectList(wrapper);
        Iterator<DeptPO> iterator = pos.iterator();
        while(iterator.hasNext()){
            ids.add(iterator.next().getId());
        }
        return ids;
    }

}
