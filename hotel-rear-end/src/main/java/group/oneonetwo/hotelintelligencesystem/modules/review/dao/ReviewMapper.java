package group.oneonetwo.hotelintelligencesystem.modules.review.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.po.ReviewPO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @author 文
* @description 针对表【review】的数据库操作Mapper
* @createDate 2022-04-19 10:40:37
* @Entity group.oneonetwo.hotelintelligencesystem.modules.review.model.po.ReviewPO
*/
@Mapper
@Repository
public interface ReviewMapper extends BaseMapper<ReviewPO> {
    public Page<ReviewVO> getPage(@Param("page") Page page, @Param("vo") ReviewVO vo);




}
