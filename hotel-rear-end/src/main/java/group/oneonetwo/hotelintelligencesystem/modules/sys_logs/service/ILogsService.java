package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.po.LogsPO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo.LogsVO;

import java.util.Collection;
import java.util.List;

/**
 * @author liustart
 * @create 2022-03-27 8:56
 */
public interface ILogsService {

    LogsPO selectOneById(String id);

    LogsVO add(LogsVO logsVO);

    LogsVO selectOneByIdReturnVO(String id);

    LogsPO save(LogsVO logsVO);

    Integer deleteById(String id);

    LogsVO saveone(LogsVO logsVO);

    void createLog(String sketch,String detail,int type,int roleLevel);

    Page<LogsVO> getPage(LogsVO logsVO);

    LogsVO findById(String id);

    List<LogsVO> getAllList(LogsVO logsVO);
}
