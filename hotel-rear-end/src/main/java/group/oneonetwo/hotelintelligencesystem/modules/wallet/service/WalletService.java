package group.oneonetwo.hotelintelligencesystem.modules.wallet.service;


import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.vo.WalletVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;

/**
* @author 文
* @description 针对表【wallet】的数据库操作Service
* @createDate 2022-04-18 14:38:01
*/
public interface WalletService {

    WalletVO getWallet(String pwd);

    Reply editPwd(String oldPwd, String newPwd);

    WalletPO getWalletPOByUid(String uid);

    WalletPO save(WalletPO walletPO);

    void editBalance(Integer mode,Double num);

    String alipayPreCreate(String mode, String value);
}
