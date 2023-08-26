package group.oneonetwo.hotelintelligencesystem.components.security.service.impl;


import group.oneonetwo.hotelintelligencesystem.components.security.entity.JwtUser;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * @author 文
 * @description 用于security获取用户对象
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserPO user = userService.findByUsername(s);
        DeptPO dept = deptService.selectOneById(user.getDept());
        return new JwtUser(user,dept.getRole());
    }

}
