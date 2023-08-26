package group.oneonetwo.hotelintelligencesystem.modules.review.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import group.oneonetwo.hotelintelligencesystem.modules.review.model.po.ReviewPO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author 文
* @description 针对表【review】的数据库操作Service
* @createDate 2022-04-19 10:40:37
*/
public interface ReviewService  {
    ReviewPO selectOneById(String id);
    ReviewVO add(ReviewVO reviewVO);
    ReviewVO selectOneByIdReturnVO(String id);
    ReviewVO save(ReviewVO reviewVO);
    Integer deleteById(String id);
    Page<ReviewVO> getPage(ReviewVO reviewVO);
    ReviewVO getCheck(ReviewVO reviewVO);
    void getReviews(ReviewVO reviewVO);

    ReviewPO selectByUID(String id);
    Page<ReviewVO> my(ReviewVO reviewVO);

    void payDeclaration(String id,String walletPwd);
    String payDeclarationForAlipay(String id) throws Exception;
    Boolean checkDeclarationPayStatusForAlipay(String id) throws Exception;
}
