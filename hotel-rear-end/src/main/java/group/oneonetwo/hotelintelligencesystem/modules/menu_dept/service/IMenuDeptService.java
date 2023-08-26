package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service;

import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;

import java.util.List;

public interface IMenuDeptService {
    MenuDeptPO selectOneById(String id);

    MenuDeptVO add(MenuDeptVO menuDeptVO);

    MenuDeptPO save(MenuDeptVO menuDeptVO);

    Integer deleteAllByMenuId(String id);

    MenuDeptVO selectOneByIdReturnVO(String id);

//    List<MenuDeptVO> getMenuDeptByDeptId()

    Integer batchAdd(String menuId, MenuVO vo);

    List<String> getDeptIdListByMenuId(String id);

    List<String> getRoleListByMenuId(String id);
}
