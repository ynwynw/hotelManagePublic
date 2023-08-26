package group.oneonetwo.hotelintelligencesystem.modules.collection.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.collection.model.po.CollectionPO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文
* @description 针对表【collection】的数据库操作Mapper
* @createDate 2022-05-10 11:02:28
* @Entity group.oneonetwo.hotelintelligencesystem.modules.collection.model.po.Collection
*/
@Mapper
public interface CollectionMapper extends BaseMapper<CollectionPO> {


}
