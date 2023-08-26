package group.oneonetwo.hotelintelligencesystem.tools;

/**
 * @author 文
 * @description 自定义字符串工具
 */
public class WStringUtils {

    /**
     * 字符串是否为空串
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        if ("".equals(s) || s == null) {
            return true;
        }
        return false;
    }

    /**
     * 去除html标签
     * @param content
     * @return
     */
    public static String removeHtml(String content) {
        if (isBlank(content)) {
            return "";
        }
        return content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
    }

}
