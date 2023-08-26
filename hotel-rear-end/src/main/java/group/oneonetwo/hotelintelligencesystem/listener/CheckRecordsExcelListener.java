package group.oneonetwo.hotelintelligencesystem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsExcelTemplate;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.service.ICheckRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 文
 * @description: 检测记录的excel插入监听器
 * @date 2022/4/19 15:04
 */
public class CheckRecordsExcelListener implements ReadListener<CheckRecordsExcelTemplate> {

    /**
     * 最多多少条数据插入一次数据库
     */
    private static final int BATCH_COUNT = 10;

    private List<CheckRecordsExcelTemplate> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private ICheckRecordsService checkRecordsService;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    public CheckRecordsExcelListener(ICheckRecordsService checkRecordsService) {
        this.checkRecordsService = checkRecordsService;
    }

    @Override
    public void invoke(CheckRecordsExcelTemplate data, AnalysisContext analysisContext) {
        Gson gson = new Gson();
        logger.info("解析到一条数据:{}", gson.toJson(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    public void saveData() {
        logger.info("{}条数据，开始存储数据库！", cachedDataList.size());
        checkRecordsService.upload(cachedDataList);
        logger.info("存储数据库成功！");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        logger.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            logger.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }
}
