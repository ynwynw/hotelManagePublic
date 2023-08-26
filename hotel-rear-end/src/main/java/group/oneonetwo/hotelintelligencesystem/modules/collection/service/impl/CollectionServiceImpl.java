package group.oneonetwo.hotelintelligencesystem.modules.collection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.modules.collection.dao.CollectionMapper;
import group.oneonetwo.hotelintelligencesystem.modules.collection.model.po.CollectionPO;
import group.oneonetwo.hotelintelligencesystem.modules.collection.service.ICollectionService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 文
* @description 针对表【collection】的数据库操作Service实现
* @createDate 2022-05-10 11:02:28
*/
@Service
public class CollectionServiceImpl implements ICollectionService {

    @Autowired
    IHotelService hotelService;

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public String collection(String id) {
        String res = null;
        String uid = authUtils.getUid();
        QueryWrapper<CollectionPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).eq("hotel_id",id);
        List<CollectionPO> list = collectionMapper.selectList(wrapper);
        if (list.isEmpty()) {
            CollectionPO collectionPO = new CollectionPO();
            collectionPO.setUid(uid);
            collectionPO.setHotelId(id);
            collectionPO.setStatus(1);
            collectionMapper.insert(collectionPO);
            res = "收藏成功";
        }else {
            CollectionPO po = list.get(0);
            if (po.getStatus() == 0) {
                po.setStatus(1);
                res = "收藏成功";
            }else {
                po.setStatus(0);
                res = "取消收藏成功";
            }
            collectionMapper.updateById(po);
        }
        return res;
    }

    @Override
    public Page<HotelVO> myCollectionList(group.oneonetwo.hotelintelligencesystem.tools.Page page) {
        String uid = authUtils.getUid();
        QueryWrapper<CollectionPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).select("hotel_id").eq("status",1);
        List<CollectionPO> collectionPOS = collectionMapper.selectList(wrapper);
        if (collectionPOS.isEmpty()) {
            return new Page<HotelVO>();
        }
        List<String> list = new ArrayList<>();
        for(CollectionPO item: collectionPOS) {
            list.add(item.getHotelId());
        }
        HotelVO hotelVO = new HotelVO();
        hotelVO.setCollection(list);
        hotelVO.setPage(page);
        return hotelService.getPage(hotelVO);
    }

    @Override
    public boolean isCollection(String id) {
        String uid = authUtils.getUid();
        QueryWrapper<CollectionPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).eq("hotel_id",id).eq("status",1);
        List<CollectionPO> collectionPOS = collectionMapper.selectList(wrapper);
        if (collectionPOS.isEmpty()) {
            return false;
        }
        return true;
    }
}
