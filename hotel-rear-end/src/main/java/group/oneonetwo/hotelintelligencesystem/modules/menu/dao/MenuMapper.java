package group.oneonetwo.hotelintelligencesystem.modules.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.po.MenuPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuPO> {

    List<MenuVO> getMenuTreeByDeptIdAndRole(@Param("vo") MenuDeptVO vo);
}
