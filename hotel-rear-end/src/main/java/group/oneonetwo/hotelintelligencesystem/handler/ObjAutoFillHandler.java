package group.oneonetwo.hotelintelligencesystem.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 文
 * @description 对象自动填充操作类（对创建人、创建时间、更新人、更新时间作填充）
 */
@Component
public class ObjAutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("createTime", randomDate("2022-5-1","2022-6-19"), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        if (uid == null) {
            uid = "0";
        }
        this.setFieldValByName("createBy", uid, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        if (uid == null) {
            uid = "0";
        }
        this.setFieldValByName("updateBy", uid, metaObject);
    }

    private Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}
