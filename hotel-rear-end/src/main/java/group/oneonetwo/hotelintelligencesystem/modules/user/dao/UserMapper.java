package group.oneonetwo.hotelintelligencesystem.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 文
 * @description user mapper类
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}
