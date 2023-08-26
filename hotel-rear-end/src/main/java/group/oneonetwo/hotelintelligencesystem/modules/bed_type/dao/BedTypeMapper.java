package group.oneonetwo.hotelintelligencesystem.modules.bed_type.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BedTypeMapper extends BaseMapper<BedTypePO>{
}
