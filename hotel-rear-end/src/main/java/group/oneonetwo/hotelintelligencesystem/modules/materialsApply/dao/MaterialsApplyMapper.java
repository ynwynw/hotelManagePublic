package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApplyPO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author 文
* @description 针对表【materials_apply】的数据库操作Mapper
* @createDate 2022-04-22 20:48:03
* @Entity group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApply
*/
@Mapper
@Repository
public interface MaterialsApplyMapper extends BaseMapper<MaterialsApplyPO> {




}
