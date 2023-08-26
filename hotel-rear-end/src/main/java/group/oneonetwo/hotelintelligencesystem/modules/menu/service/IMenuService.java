package group.oneonetwo.hotelintelligencesystem.modules.menu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.po.MenuPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;

import java.util.List;

public interface IMenuService {
    MenuVO add(MenuVO vo);

    Integer delete(String id);

    List<MenuVO> getMenuTreeByDeptIdAndRole(MenuDeptVO vo);

    MenuVO selectByIdReturnVO(String id);

    List<MenuVO> getMenuTree(MenuVO menuVO);

    MenuPO save(MenuVO menuVO);

    MenuVO saveone(MenuVO menuVO);

    Page<MenuVO> getPage(MenuVO menuVO);

}
