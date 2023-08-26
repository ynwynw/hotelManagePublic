package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApplyPO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;

/**
* @author 文
* @description 针对表【materials_apply】的数据库操作Service
* @createDate 2022-04-22 20:48:03
*/
public interface IMaterialsApplyService {

    MaterialsApplyVO add(MaterialsApplyVO materialsApplyVO);

    MaterialsApplyVO apply(MaterialsApplyVO materialsApplyVO);

    Page<MaterialsApplyVO> getPage(MaterialsApplyVO materialsApplyVO);

    MaterialsApplyVO selectOneByIdReturnVO(String id);

    MaterialsApplyPO selectOneById(String id);

    Integer deleteById(String id);

    MaterialsApplyVO save(MaterialsApplyVO materialsApplyVO);

    void review(MaterialsApplyVO materialsApplyVO);


    Page<MaterialsApplyVO> getReviewPage(MaterialsApplyVO materialsApplyVO);
}
