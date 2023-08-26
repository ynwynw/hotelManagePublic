package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.po.LogsPO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo.LogsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 文
 * @description 日志数据操作层
 */
@Mapper
@Repository
public interface LogsMapper extends BaseMapper<LogsPO> {
    Page<LogsVO> getPage(Page<LogsVO> page, LogsVO vo);

    LogsVO findById(String id);

    List<LogsVO> getAllList(@Param("vo") LogsVO vo);

}
