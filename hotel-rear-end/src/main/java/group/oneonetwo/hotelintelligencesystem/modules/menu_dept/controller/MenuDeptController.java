package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.dao.MenuDeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.IMenuDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author 文
 */
@Api(tags="菜单部门相关接口")
@RestController
@RequestMapping("api/menuDept")
public class MenuDeptController {

    @Autowired
    IMenuDeptService menuDeptService;

    @GetMapping("list/deptId/{id}")
    public Reply<List<String>> getDeptIdListByMenuId(@PathVariable("id") String id) {
        return Reply.success(menuDeptService.getDeptIdListByMenuId(id));
    }

    @GetMapping("list/role/{id}")
    public Reply<List<String>> getRoleListByMenuId(@PathVariable("id") String id) {
        return Reply.success(menuDeptService.getRoleListByMenuId(id));
    }

}
