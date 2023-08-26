package group.oneonetwo.hotelintelligencesystem.tools;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 文
 * @description 转换工具类
 */
public class ConvertUtils {

    /**
     * list泛型转换
     * @param sources
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> transferList(List<?> sources, Class<T> clazz) {
        List list = new ArrayList<T>();
        if (sources == null) {
            return list;
        } else {
            Iterator var3 = sources.iterator();

            while(var3.hasNext()) {
                Object o = var3.next();
                T t = null;
                try {
                    t = clazz.newInstance();
                } catch (InstantiationException e) {
                    throw new CommonException(500,"InstantiationException:" + e.getMessage());
                } catch (IllegalAccessException e) {
                    throw new CommonException(500,"IllegalAccessException:" + e.getMessage());
                }
                BeanUtils.copyProperties(o,t);
                list.add(t);
            }

            return list;
        }
    }

    /**
     * Page转换
     * @param sourcePage
     * @param targetClass
     * @param <T2>
     * @return
     */
    public static <T2> Page<T2> transferPage(Page sourcePage, Class<T2> targetClass) {
        Page<T2> targetPage = new Page<>();
        targetPage.setPages(sourcePage.getPages());
        targetPage.setCurrent(sourcePage.getCurrent());
        targetPage.setSize(sourcePage.getSize());
        targetPage.setTotal(sourcePage.getTotal());
        targetPage.setOrders(sourcePage.getOrders());
        if (sourcePage.getRecords() != null) {
            List<T2> targetList = ConvertUtils.transferList(sourcePage.getRecords(), targetClass);
            targetPage.setRecords(targetList);
        }
        return targetPage;
    }

}
