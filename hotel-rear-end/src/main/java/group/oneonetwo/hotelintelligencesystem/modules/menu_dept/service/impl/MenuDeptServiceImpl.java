package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.dao.MenuDeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.IMenuDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class MenuDeptServiceImpl implements IMenuDeptService {

    @Autowired
    MenuDeptMapper menuDeptMapper;

    @Override
    public MenuDeptPO selectOneById(String id) {
        return null;
    }

    @Override
    public MenuDeptVO add(MenuDeptVO menuDeptVO) {
        return null;
    }

    @Override
    public MenuDeptPO save(MenuDeptVO menuDeptVO) {
        return null;
    }

    @Override
    public Integer deleteAllByMenuId(String id) {
        QueryWrapper<MenuDeptPO> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_id",id);
        int delete = menuDeptMapper.delete(wrapper);
        return delete;
    }

    @Override
    public MenuDeptVO selectOneByIdReturnVO(String id) {
        return null;
    }

    @Override
    public Integer batchAdd(String menuId, MenuVO vo) {
        deleteAllByMenuId(menuId);
        String[] ids = vo.getDeptId().split(",");
        String[] roles = vo.getRole().split(",");
        Iterator<String> deptIterator = Arrays.stream(ids).iterator();
        Iterator<String> rolesIterator = Arrays.stream(roles).iterator();

        int addNum = 0;
        while (deptIterator.hasNext()) {
            MenuDeptPO po = new MenuDeptPO();
            String next = deptIterator.next();
            if (WStringUtils.isBlank(next)) {
                break;
            }
            po.setMenuId(menuId);
            po.setDeptId(next);
            int insert = menuDeptMapper.insert(po);
            if (insert != 0){
                addNum++;
            }
        }

        while (rolesIterator.hasNext()) {
            MenuDeptPO po = new MenuDeptPO();
            String next = rolesIterator.next();
            if (WStringUtils.isBlank(next)) {
                break;
            }
            po.setMenuId(menuId);
            po.setRole(next);
            int insert = menuDeptMapper.insert(po);
            if (insert != 0){
                addNum++;
            }
        }
        return addNum;
    }

    @Override
    public List<String> getDeptIdListByMenuId(String id) {
        QueryWrapper<MenuDeptPO> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_id",id).select("dept_id","menu_id").isNotNull("dept_id");
        List<MenuDeptPO> menuDeptPOS = menuDeptMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < menuDeptPOS.size(); i++) {
            list.add(menuDeptPOS.get(i).getDeptId());
        }
        return list;
    }

    @Override
    public List<String> getRoleListByMenuId(String id) {
        QueryWrapper<MenuDeptPO> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_id",id).select("role","menu_id").isNotNull("role");
        List<MenuDeptPO> menuDeptPOS = menuDeptMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < menuDeptPOS.size(); i++) {
            list.add(menuDeptPOS.get(i).getRole());
        }
        return list;
    }
}
