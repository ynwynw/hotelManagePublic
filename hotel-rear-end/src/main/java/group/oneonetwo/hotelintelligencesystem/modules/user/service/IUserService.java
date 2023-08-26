package group.oneonetwo.hotelintelligencesystem.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;

public interface IUserService {

    UserPO add(UserVO userVO);

    UserVO selectOneByIdReturnVO(String id);

    UserPO save(UserVO userVO);

    Integer deleteById(String id);

    Reply selectOneById(String id);

    UserPO findByUsername(String s);

    Reply register(UserVO userVO) throws SavaException;

    Reply<UserVO> update(UserVO userVO);

    UserVO addOneUser(UserVO userVO);

    Page<UserVO> getPage(UserVO userVO);

    void addTestData();
}
