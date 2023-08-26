package group.oneonetwo.hotelintelligencesystem.modules.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文
 * @description user控制层
 */
@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    IUserService userService;

    @ApiOperation("根据id查询用户")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "1", required = true)
    @GetMapping("get/{id}")
    public Reply<UserVO> selectOneById(@PathVariable("id") String id) {
        return userService.selectOneById(id);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("modify")
    public Reply<UserVO> save(@RequestBody UserVO userVO) {
        return userService.update(userVO);
    }

    @ApiOperation("新增用户")
    @PostMapping("add")
    public Reply<UserVO> add(@RequestBody UserVO userVO) {
        return Reply.success(userService.addOneUser(userVO));
    }

    @ApiOperation("根据id删除用户")
    @GetMapping("delete/{id}")
    public  Reply deleteById(@PathVariable("id") String id){
        return userService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("page")
    @ApiOperation("获取用户列表(分页)")
    public Reply<Page<UserVO>> getPage(@RequestBody UserVO userVO){
        return Reply.success(userService.getPage(userVO));
    }
}
