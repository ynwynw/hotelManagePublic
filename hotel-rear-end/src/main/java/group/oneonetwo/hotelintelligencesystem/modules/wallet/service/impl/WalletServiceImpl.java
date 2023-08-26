package group.oneonetwo.hotelintelligencesystem.modules.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.dao.WalletMapper;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.vo.WalletVO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 文
* @description 针对表【wallet】的数据库操作Service实现
* @createDate 2022-04-18 14:38:01
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletMapper walletMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthUtils authUtils;

    @Override
    public WalletVO getWallet(String pwd) {
        String uid = authUtils.getUid();
        QueryWrapper<WalletPO> wrapper = new QueryWrapper<WalletPO>();
        wrapper.eq("u_id",uid);
        WalletPO walletPO = walletMapper.selectOne(wrapper);
        if (walletPO == null) {
            walletPO = createAccount(uid,pwd);
        }else {
            boolean matches = bCryptPasswordEncoder.matches(pwd, walletPO.getPassword());
            if (!matches) {
                throw new CommonException("账户密码错误");
            }
        }
        WalletVO walletVO = new WalletVO();
        BeanUtils.copyProperties(walletPO,walletVO);
        return walletVO;
    }

    @Override
    public Reply editPwd(String oldPwd, String newPwd) {
        String uid = authUtils.getUid();
        QueryWrapper<WalletPO> wrapper = new QueryWrapper<WalletPO>();
        wrapper.eq("u_id",uid);
        WalletPO walletPO = walletMapper.selectOne(wrapper);
        boolean matches = bCryptPasswordEncoder.matches(oldPwd, walletPO.getPassword());
        if (matches) {
            walletPO.setPassword(bCryptPasswordEncoder.encode(newPwd));
            int i = walletMapper.updateById(walletPO);
            if (i == 0) {
                throw new CommonException("密码更新异常");
            }
        }else {
            throw new CommonException("账户密码错误");
        }
        return Reply.success();
    }

    private WalletPO createAccount(String uid,String pwd) {
        WalletPO walletPO = new WalletPO();
        walletPO.setPassword(bCryptPasswordEncoder.encode(pwd));
        walletPO.setuId(uid);
        int insert = walletMapper.insert(walletPO);
        if (insert == 0) {
            throw new CommonException("创建账户失败");
        }
        walletPO.setBalance(0.00);
        return walletPO;

    }

    @Override
    public WalletPO getWalletPOByUid(String uid){
        QueryWrapper<WalletPO> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",uid);
        List<WalletPO> walletPOS = walletMapper.selectList(wrapper);

        try {
            walletPOS.get(0);
        } catch (Exception e) {
            throw new CommonException("该用户未开通账户");
            }
        return walletPOS.get(0);
    }

    @Override
    public WalletPO save(WalletPO walletPO) {
        if (walletPO==null){
            throw new CommonException(501,"钱包实体为空");
        }
        int i = walletMapper.updateById(walletPO);
        if(i>0){
            return walletPO;
        }
        throw new SavaException("钱包修改时异常");
    }

    @Override
    public void editBalance(Integer mode, Double num) {
        WalletPO current = getWalletPOByUid(authUtils.getUid());
        WalletPO walletPO = new WalletPO();
        walletPO.setId(current.getId());
        switch (mode) {
            case 0:
                walletPO.setBalance(0.00);
                break;
            case 1:
                walletPO.setBalance(current.getBalance()+num);
                break;
            case 2:
                walletPO.setBalance(current.getBalance()-num);
                if (walletPO.getBalance() < 0.00) {
                    throw new CommonException("余额不足");
                }
                break;
            default:
                throw new CommonException("非法操作");
        }
        WalletPO save = save(walletPO);
    }

    @Override
    public String alipayPreCreate(String mode, String value) {
        return null;
    }
}
