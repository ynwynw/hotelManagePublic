package group.oneonetwo.hotelintelligencesystem.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.modules.user.dao.UserMapper;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 文
 * @description 用户服务层实现类
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private IDeptService deptService;

    @Autowired
    LogsService logsService;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);


    @Override
    public UserPO add(UserVO userVO){
        if (userVO == null) {
            throw new SavaException("插入用户失败：user实体为空");
        }
        if(userVO.getUsername()==null){
            throw new SavaException("用户名不能为空");
        }
        UserPO userPO1=new UserPO();
        userPO1=this.findByUsername(userVO.getUsername());
        if(userPO1!=null){
            throw  new SavaException("该用户已存在");
        }

        userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO,userPO);
        int insert = userMapper.insert(userPO);
        if (insert > 0) {
            return userPO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public UserVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501,"id参数为空");
        }
        UserPO userPO = userMapper.selectById(id);
        UserVO userVO = new UserVO();
        if (userPO != null) {
            BeanUtils.copyProperties(userPO,userVO);
        }
        return userVO;
    }

    @Override
    public UserPO save(UserVO userVO){
        if (userVO == null) {
            throw new CommonException(501,"user实体为空");
        }
        UserVO check = selectOneByIdReturnVO(userVO.getId());
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + userVO.getId() + "'的数据");
        }
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO,userPO);
        int save = userMapper.updateById(userPO);
        if (save > 0) {
            return userMapper.selectById(userPO.getId());
        }
        throw new SavaException("更改用户失败");
    }

    @Override
    public Integer deleteById(String id) {
        UserVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + id + "'的数据");
        }
        Gson gson = new Gson();
        logsService.createLog("【删除】用户信息",gson.toJson(check),1,0);
        int i = userMapper.deleteById(id);
        return i;
    }

    @Override
    public Reply<UserVO> selectOneById(String id) {
        UserVO userVO = selectOneByIdReturnVO(id);
        if (userVO == null) {
            return Reply.noData();
        }
        return Reply.success(userVO);
    }

    @Override
    public UserPO findByUsername(String s) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<UserPO>();
        wrapper.eq("username",s).eq("is_deleted",0);
        UserPO userPO = userMapper.selectOne(wrapper);
        return userPO;
    }

    @Override
    public Reply register(UserVO userVO) throws SavaException {
        userVO.setReviewStatus(1);

        //此处根据数据库进行设置,这边普通成员的deptId为10
        userVO.setDept("10");
        UserPO add = this.add(userVO);
        BeanUtils.copyProperties(add,userVO);
        Gson gson = new Gson();
        logsService.createLog("【注册】新用户注册",gson.toJson(userVO),1,0);
        return Reply.success(userVO);
    }

    @Override
    public Reply<UserVO> update(UserVO userVO) {
        UserVO before = selectOneByIdReturnVO(userVO.getId());
        if (!"".equals(userVO.getPassword()) && userVO.getPassword() != null) {
            userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        } else {
            userVO.setPassword(null);
        }
        UserPO save = save(userVO);
        BeanUtils.copyProperties(save,userVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】用户信息",gson.toJson(before) + "@*@" + gson.toJson(userVO),1,0);

        return Reply.success(userVO);
    }

    @Override
    public UserVO addOneUser(UserVO userVO) {
        UserPO add = add(userVO);
        BeanUtils.copyProperties(add,userVO);
        Gson gson = new Gson();
        logsService.createLog("【添加】用户信息",gson.toJson(userVO),1,0);
        return userVO;
    }

    @Override
    public Page<UserVO> getPage(UserVO userVO){
        QueryWrapper<UserPO> wrapper=new QueryWrapper<>();
        //权限控制
        String role = authUtils.getRole();
        switch (role) {
            case "admin" :
                break;
            case "hotel_admin":
                List<String> hotelAllUser = authUtils.getHotelAllUser(authUtils.getUserHotelId());
                for (int i = 0; i < hotelAllUser.size(); i++) {
                    if (hotelAllUser.get(i).equals(authUtils.getUid())) {
                        hotelAllUser.remove(i);
                    }
                }
                wrapper.in("id",hotelAllUser);

        }
        if (!WStringUtils.isBlank(userVO.getUsername())) {
            wrapper.like("username",userVO.getUsername());
        }
        if (!WStringUtils.isBlank(userVO.getNickname())) {
            wrapper.like("nickname",userVO.getNickname());
        }
        Page<UserPO> page=new Page<>(userVO.getPage().getPage(),userVO.getPage().getSize());
        Page<UserPO> poiPage=(Page<UserPO>) userMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,UserVO.class);
    }

    @Override
    public void addTestData() {
        String[] nameArr = {"陈金玲", "蓝文悦", "王健宇", "李海泉", "许嗣益", "宋沛蓉", "何梅敏", "陆彩飞", "蔡晓玲",
                "杨凯竣", "黎浩存", "胡馨月", "吴柏青", "詹宏水", "罗家成", "曾锦涛", "钟展政", "王志斌", "徐铱煊",
                "黄智群", "李宁", "钱宇轩", "林文俊", "蓝昭豪", "杨娟", "董迎春", "何嘉诚", "蔡权纬", "陈奕斌", "吴沛蓉",
                "黄伟昊", "关卓耀", "彭琨", "吴忠劲", "何鸿业", "姜亚伟", "卢俊晓", "王镜清", "柯炳奕", "梁东升", "黄泰彬",
                "林焕然", "谢锡昭", "欧阳俊辉", "夏雨飞", "黄天疆", "雷镇桂", "梁健", "高雅", "李瑞峥", "温毅豪"
        };
        String pwd = "654321";
        int i = 1001;
        for (String s :
                nameArr) {
            UserVO userVO = new UserVO();
            userVO.setUsername("testUser" + i);
            userVO.setPassword(pwd);
            userVO.setNickname(s);
            register(userVO);
            i++;
        }
    }

}
