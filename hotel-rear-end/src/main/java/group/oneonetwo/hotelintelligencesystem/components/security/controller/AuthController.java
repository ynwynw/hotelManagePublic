package group.oneonetwo.hotelintelligencesystem.components.security.controller;

import group.oneonetwo.hotelintelligencesystem.components.security.entity.BaseUser;
import group.oneonetwo.hotelintelligencesystem.components.security.entity.ScanVO;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.JwtTokenUtils;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.RedisUtil;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author 文
 * @description 权限的相关视图层
 */
@Api(tags = "权限")
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IDeptService deptService;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册用户
     * @author 文
     * @param userVO
     * @return
     */
    @ApiOperation("注册用户")
    @PostMapping("open/auth/register")
    public Reply registerUser(@RequestBody UserVO userVO){
        return userService.register(userVO);
    }

    @PostMapping("/test")
    @ApiOperation("登录接口【POST】:\"/auth/login\"，需要参数:username,password")
    public void login(){

    }


    @PostMapping("open/test1")
    public Reply registerUser1(){
        userService.addTestData();
        return Reply.success();
    }

    /**
     * 二维码登录相关
     * @param type 1为获取二维码,2为检查二维码状态
     * @return
     */
    @GetMapping("open/auth/qrCode/{type}")
    public Reply qrCode(@PathVariable("type") String type,@RequestParam String code) {
        switch (type) {
            case "1":
                String s = UUID.randomUUID().toString();
                redisUtil.set(s,"0");
                redisUtil.setKeyExpire(s,192000L);
                return Reply.success(s);
            case "2":
                HashMap<String, Object> map = new HashMap<>();
                String[] s1 = redisUtil.get(code).toString().split("-");
                String fullToken = "";
                for (int i = 1; i < s1.length; i++) {
                    if (i != 1) {
                        fullToken += "-";
                    }
                    fullToken += s1[i];
                }
                String status = s1[0];
                map.put("status",status);
                if ("2".equals(status)) {
                    String[] tokens = fullToken.split(" ");
                    if (!jwtTokenUtils.TOKEN_PREFIX.equals(tokens[0])) {
                        return Reply.failed(ResultCode.ILLEGAL_TOKEN.getCode(),ResultCode.ILLEGAL_TOKEN.getMsg());
                    }
                    String token = tokens[1];
                    map.put("token",fullToken);
                    logger.info(token);
                    logger.info("Full Token:" + fullToken);
                    String uid = jwtTokenUtils.getUsername(token);
                    UserVO userVO = userService.selectOneByIdReturnVO(uid);
                    MenuDeptVO vo = new MenuDeptVO();
                    vo.setRole(jwtTokenUtils.getUserRole(token));
                    vo.setDeptId(userVO.getDept());
                    List<MenuVO> menuTree = menuService.getMenuTreeByDeptIdAndRole(vo);
                    DeptVO deptVO = deptService.selectOneByIdReturnVO(userVO.getDept());
                    BaseUser baseUser = new BaseUser();
                    BeanUtils.copyProperties(userVO,baseUser);
                    baseUser.setRole(deptVO.getRole());
                    baseUser.setDeptName(deptVO.getName());
                    map.put("userInfo",baseUser);
                    map.put("menuList",menuTree);
                }
                return Reply.success(map);
            default:
                return Reply.failed("错误的类型码");
        }
    }

    /**
     * 扫描
     * @param scanVO type:1为扫描预发送,2为确认
     * @return
     */
    @PostMapping("/auth/scan")
    public Reply scan(@RequestBody ScanVO scanVO) {
        logger.info("type:" + scanVO.getType());
        logger.info("qrCode:" + scanVO.getQrCode());
        logger.info("token:" + scanVO.getToken());
        switch (scanVO.getType()) {
            case "1":
                if (redisUtil.hasKey(scanVO.getQrCode())) {
                    redisUtil.set(scanVO.getQrCode(),"1");
                    return Reply.success();
                }
                return Reply.failed("二维码已过期");
            case "2":
                if (redisUtil.hasKey(scanVO.getQrCode())) {
                    String res = "2-" + scanVO.getToken();
                    redisUtil.set(scanVO.getQrCode(),res);
                    return Reply.success();
                }
                return Reply.failed("二维码已过期");
            default:
                return Reply.failed("错误的类型码");
        }
    }

}
