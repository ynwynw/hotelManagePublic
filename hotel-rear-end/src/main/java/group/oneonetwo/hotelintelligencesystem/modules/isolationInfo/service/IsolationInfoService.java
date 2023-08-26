package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;

import java.util.List;

/**
* @author 文
* @description 针对表【isolation_info】的数据库操作Service
* @createDate 2022-04-19 10:26:02
*/
public interface IsolationInfoService{

    IsolationInfoVO add(IsolationInfoVO isolationInfoVO);

    IsolationInfoVO save(IsolationInfoVO isolationInfoVO);

    Integer deleteById(String id);

    IsolationInfoVO selectOneByIdReturnVO(String id);

    IsolationInfoPO selectOneById(String id);

    Page<IsolationInfoVO> getPage(IsolationInfoVO isolationInfoVO);

    List<IsolationInfoVO> getAllList(IsolationInfoVO isolationInfoVO);

    /**
     * 获取某个房间正在隔离的人员信息
     * @param roomId
     * @return
     */
    IsolationInfoPO selectOneByRoomId(String roomId);

    Page<IsolationInfoVO> getOwnPage(IsolationInfoVO isolationInfoVO);


    void distribution(IsolationInfoVO isolationInfoVO);

    IsolationInfoVO selectByRoomIdAndStaus(String roomId);

    IsolationInfoVO test();
}
