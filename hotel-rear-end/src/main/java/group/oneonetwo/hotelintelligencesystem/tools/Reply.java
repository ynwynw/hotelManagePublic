package group.oneonetwo.hotelintelligencesystem.tools;

import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;

import java.io.Serializable;

/**
 * @author 文
 * @description 统一返回格式类
 */
public class Reply<T> implements Serializable {
    private String code;
    private T data;
    private Long time = System.currentTimeMillis();
    private String msg;
    private Object annex;

    public <T> Reply() {
    }

    public Reply(String code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public static <T> Reply<T> success() {
        return restResult(null, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static <T> Reply<T> success(T data) {
        return restResult(data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static <T> Reply<T> success(String msg, T data) {
        return restResult(data, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> Reply<T> failed() {
        return restResult(null, ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg());
    }

    public static <T> Reply<T> failed(T data) {
        return restResult(data, ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg());
    }

    public static <T> Reply<T> failed(String msg, T data) {
        return restResult(data, ResultCode.FAILED.getCode(), msg);
    }

    public static <T> Reply<T> failed( String code, String msg, T data) {
        return restResult(data, code, msg);
    }

    public static <T> Reply noData() {
        return restResult(null, ResultCode.NO_DATA.getCode(), ResultCode.NO_DATA.getMsg());
    }

    private static <T> Reply<T> restResult(T data, String code, String msg) {
        Reply<T> apiResult = new Reply();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getAnnex() {
        return annex;
    }

    public void setAnnex(Object annex) {
        this.annex = annex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":\"")
                .append(code).append('\"');
        sb.append(",\"data\":")
                .append(data);
        sb.append(",\"time\":")
                .append(time);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append(",\"annex\":")
                .append(annex);
        sb.append('}');
        return sb.toString();
    }
}
