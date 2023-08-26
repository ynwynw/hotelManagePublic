package group.oneonetwo.hotelintelligencesystem.modules.collection.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.collection.service.ICollectionService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文
 * @description: 收藏控制层
 * @date 2022/5/10 11:10
 */
@Api(tags="收藏相关接口")
@RestController
@RequestMapping("api/collection")
public class CollectionController {

    @Autowired
    ICollectionService collectionService;

    @ApiOperation("获取当前酒店收藏状态")
    @GetMapping("isCollection/{hotelId}")
    public Reply isCollection(@PathVariable("hotelId") String id) {
        return Reply.success(collectionService.isCollection(id));
    }

    @ApiOperation("收藏/取消收藏")
    @PostMapping("collection")
    public Reply collection(String id) {
        return Reply.success(collectionService.collection(id));
    }

    @ApiOperation("获取我的收藏")
    @PostMapping("myCollectionList")
    public Reply<Page<HotelVO>> myCollectionList(@RequestBody group.oneonetwo.hotelintelligencesystem.tools.Page page) {
        return Reply.success(collectionService.myCollectionList(page));
    }

}
