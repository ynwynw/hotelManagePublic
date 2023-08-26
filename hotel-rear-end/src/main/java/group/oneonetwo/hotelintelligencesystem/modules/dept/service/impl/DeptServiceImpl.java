package group.oneonetwo.hotelintelligencesystem.modules.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.dao.DeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.impl.UserServiceImpl;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author 文
 * @description 部门服务层实现类
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DeptServiceImpl implements IDeptService {

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    LogsService logsService;

    @Override
    public DeptPO add(DeptVO deptVO) {
        if (deptVO == null) {
            throw new SavaException("插入用户失败：部门实体为空");
        }
        DeptPO deptPO = new DeptPO();
        BeanUtils.copyProperties(deptVO,deptPO);
        int insert = deptMapper.insert(deptPO);
        if (insert > 0) {
            return deptPO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public DeptVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501,"参数为空");
        }
        DeptPO deptPO = deptMapper.selectById(id);
        DeptVO deptVO = new DeptVO();
        if (deptPO != null) {
            BeanUtils.copyProperties(deptPO,deptVO);
        }
        return deptVO;
    }

    @Override
    public DeptPO save(DeptVO deptVO) {
        if (deptVO == null) {
            throw new CommonException(501,"dept实体为空");
        }
        DeptVO check = selectOneByIdReturnVO(deptVO.getId());
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + deptVO.getId() + "'的数据");
        }
        DeptPO deptPO = new DeptPO();
        BeanUtils.copyProperties(deptVO,deptPO);
        int save = deptMapper.updateById(deptPO);
        if (save > 0) {
            return deptMapper.selectById(deptPO.getId());
        }
        throw new SavaException("更改部门失败");
    }

    @Override
    public Integer deleteById(String id) {
        DeptVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + id + "'的数据");
        }
        Gson gson = new Gson();
        logsService.createLog("【删除】部门信息",gson.toJson(check),1,1);
        int i = deptMapper.deleteById(id);
        return i;
    }

    @Override
    public Reply batchAdd(List<DeptVO> deptVOS) {

        Iterator<DeptVO> iterator = deptVOS.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return Reply.success();
    }

    @Override
    public Page<DeptVO> getPage(DeptVO deptVO) {
        String authority = authUtils.getRole();
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authUtils.getUid());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                deptVO.setpId(userVO.getDept());
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        Page<DeptVO> page = new Page<>(deptVO.getPage().getPage(),deptVO.getPage().getSize());
        return deptMapper.getPage(page,deptVO);
    }

    @Override
    public DeptVO insertOne(DeptVO deptVO) {
        DeptPO deptPO = selectOneById(deptVO.getId());
        if (deptPO != null) {
            throw new CommonException("已存在该实例");
        }
        DeptPO add = add(deptVO);
        BeanUtils.copyProperties(add,deptVO);
        Gson gson = new Gson();
        logsService.createLog("【添加】部门信息",gson.toJson(deptVO),1,1);
        return deptVO;
    }

    @Override
    public DeptVO saveOne(DeptVO deptVO) {
        DeptVO before = selectOneByIdReturnVO(deptVO.getId());
        DeptPO save = save(deptVO);
        BeanUtils.copyProperties(save,deptVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】部门信息",gson.toJson(before) + "@*@" + gson.toJson(save),1,1);
        return deptVO;
    }

    @Override
    public List<DeptVO> getList(DeptVO deptVO) {
        QueryWrapper<DeptPO> wrapper = new QueryWrapper<>();
        String authority = authUtils.getRole();
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authUtils.getUid());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                wrapper.in("id",authUtils.getHotelAllDept(authUtils.getUserHotelId()));
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        // 构造条件
        if (!"".equals(deptVO.getName()) && deptVO.getName() != null) {
            wrapper.like("name",deptVO.getName());
        }
        if (!"".equals(deptVO.getStatus()) && deptVO.getStatus() != null) {
            wrapper.eq("status",deptVO.getStatus());
        }
        wrapper.orderByAsc("p_id").orderByAsc("sort");
        List<DeptPO> pos = deptMapper.selectList(wrapper);
        List<DeptVO> allDept = ConvertUtils.transferList(pos, DeptVO.class);
        List<DeptVO> resDept = new ArrayList<>();
        for (DeptVO item : allDept) {
            if (!"0".equals(item.getpId())) {
                break;
            }
            DeptVO deptVO1 = addChild(item,allDept);
            resDept.add(deptVO1);
        }
        return resDept;
    }

    @Override
    public List<DeptVO> selectByWrapper(QueryWrapper wrapper) {
        List<DeptPO> list = deptMapper.selectList(wrapper);
        List<DeptVO> deptVOS = ConvertUtils.transferList(list, DeptVO.class);
        return deptVOS;
    }

    private DeptVO addChild(DeptVO current,List<DeptVO> allDept) {
        List<DeptVO> children = new ArrayList<>();
        for (DeptVO item : allDept) {
            if (item.getpId().equals(current.getId())) {
                item = addChild(item,allDept);
                children.add(item);
            }
        }
        current.setChildren(children);
        return current;
    }


    @Override
    public DeptPO selectOneById(String id) {
        DeptPO deptPO = deptMapper.selectById(id);
        return deptPO;
    }

}
