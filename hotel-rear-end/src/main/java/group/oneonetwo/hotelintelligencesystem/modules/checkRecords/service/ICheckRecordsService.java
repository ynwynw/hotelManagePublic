package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsExcelTemplate;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsVO;

import java.util.List;

/**
* @author 文
* @description 针对表【check_records】的数据库操作Service
* @createDate 2022-04-19 14:07:07
*/
public interface ICheckRecordsService {

    public int upload(List<CheckRecordsExcelTemplate> list);

    public int batchInsert(List<CheckRecordsVO> list);

    int insertManually(CheckRecordsVO checkRecordsVO);

    CheckRecordsVO add(CheckRecordsVO checkRecordsVO);

    int deleteById(String id);

    CheckRecordsVO save(CheckRecordsVO checkRecordsVO);


    CheckRecordsVO selectOneByIdReturnVO(String id);

    Page<CheckRecordsVO> getPages(CheckRecordsVO checkRecordsVO);

    Page<CheckRecordsVO> getOwnPage(CheckRecordsVO checkRecordsVO);
}
