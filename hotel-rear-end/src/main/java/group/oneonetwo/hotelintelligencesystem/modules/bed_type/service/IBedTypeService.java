package group.oneonetwo.hotelintelligencesystem.modules.bed_type.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;

public interface IBedTypeService {
    BedTypePO selectOneById(String id);

    BedTypeVO add(BedTypeVO bedTypeVO);

    BedTypeVO selectOneByIdReturnVO(String id);

    BedTypePO save(BedTypeVO bedTypeVO);

    Integer deleteById(String id);

    BedTypeVO saveone(BedTypeVO bedTypeVO);

    Page<BedTypeVO> getPage(BedTypeVO bedTypeVO);



}
