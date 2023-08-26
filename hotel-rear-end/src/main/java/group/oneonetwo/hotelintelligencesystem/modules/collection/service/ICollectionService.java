package group.oneonetwo.hotelintelligencesystem.modules.collection.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;

/**
* @author 文
* @description 针对表【collection】的数据库操作Service
* @createDate 2022-05-10 11:02:28
*/
public interface ICollectionService {

    String collection(String id);

    Page<HotelVO> myCollectionList(group.oneonetwo.hotelintelligencesystem.tools.Page page);

    boolean isCollection(String id);
}
