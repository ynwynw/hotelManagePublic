package group.oneonetwo.hotelintelligencesystem.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 文
 * @description 时间操作工具类
 */
public class TimeUtils {

    /**
     * 设置分割时间,一般酒店的分割时间为下午2点
     * @param time
     * @return
     */
    public static Date setSplitTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     * @param startDate 较小的时间
     * @param endDate  较大的时间
     * @param mode 模式: ceil向上取整,floor向下取整
     * @return 相差天数
     */
    public static int daysBetween(Date startDate,Date endDate,String mode){
        long diffDay = (endDate.getTime() - startDate.getTime()) / (1000 * 24 * 60 * 60);
        switch (mode){
            case "ceil":
                return (int) Math.ceil(diffDay);
            case "floor":
                return (int) Math.floor(diffDay);
            default:
                return 0;
        }
    }


}
