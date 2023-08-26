package group.oneonetwo.hotelintelligencesystem.modules.dept.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 文
 * @description dept数据操作层
 */
@Mapper
@Repository
public interface DeptMapper extends BaseMapper<DeptPO> {
    Page<DeptVO> getPage(@Param("page") Page<DeptVO> page,@Param("vo") DeptVO vo);
}
