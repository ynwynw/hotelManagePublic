package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 */
public enum ResultCode {
    //权限相关
    USER_LOGIN_SUCCESS("200","用户登录成功"),
    USER_LOGOUT_SUCCESS("200","用户登出成功"),
    USER_NO_ACCESS("4010","用户无权访问"),
    NO_LOGIN("4011","用户未登录"),
    USERNAME_PWD_ERROR("4012","用户名或密码错误"),
    TOKEN_IS_BLACKLIST("4013","此token为黑名单"),
    LOGIN_IS_OVERDUE("4014","登录已失效"),
    ILLEGAL_TOKEN("4015","非法token"),
    TOKEN_EXPIRED("4016","登录状态过期"),

    //通用
    SUCCESS("200", "操作成功"),
    NOT_FOUND("404", "无效的请求"),
    FAILED("500", "服务器错误"),
    NO_DATA("600","找不到该数据"),
    UNKNOWN("9999", "未知错误"),
    VALIDATE_FAILED("5000", "参数校验失败"),

    //异常
    RUNTIME_EX("1001","运行时异常"),
    NULL_POINT_EX("1002","空指针异常"),
    CONVERT_EX("1003","类型转换异常"),
    IO_EX("1004","IO异常"),
    UNKNOWN_FUN_EX("1005","未知方法异常"),
    ARR_OUT_OF_BOUND_EX("1006","数组越界异常"),
    HTTP_MSG_CANNOT_READ_EX("1007","400错误:Http消息不可读异常"),
    TYPE_NOT_MATCH_EX("1008","400错误:类型不匹配异常"),
    SERVLET_PARAMETER_MISS_EX("1009","400错误:缺少Servlet请求参数异常"),
    HTTP_405_EX("1010","405错误"),
    HTTP_406_EX("1011","406错误"),
    HTTP_500_EX("1012","500错误"),
    STACK_OVERFLOW_EX("1013","栈溢出"),
    DIVISOR_CANNOT_BE_ZERO_EX("1014","除数不能为0"),
    HTTP_404_EX("1015","404错误"),
    OTHER_EX("1016","其他系统级错误");

    private String code;
    private String msg;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
