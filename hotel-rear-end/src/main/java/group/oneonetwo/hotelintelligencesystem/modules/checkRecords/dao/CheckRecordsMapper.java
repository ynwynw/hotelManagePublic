package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po.CheckRecordsPO;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 文
* @description 针对表【check_records】的数据库操作Mapper
* @createDate 2022-04-19 14:07:07
* @Entity group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po.CheckRecords
*/
@Repository
@Mapper
public interface CheckRecordsMapper extends BaseMapper<CheckRecordsPO> {
    Page<CheckRecordsVO> getPages(@Param("page") Page<CheckRecordsVO> page, @Param("vo") CheckRecordsVO vo);
    int batchInsert(List<CheckRecordsPO> list);
}
