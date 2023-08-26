package group.oneonetwo.hotelintelligencesystem.modules.wallet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 文
* @description 针对表【wallet】的数据库操作Mapper
* @createDate 2022-04-18 14:38:01
* @Entity group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO
*/
@Mapper
@Repository
public interface WalletMapper extends BaseMapper<WalletPO> {


}
