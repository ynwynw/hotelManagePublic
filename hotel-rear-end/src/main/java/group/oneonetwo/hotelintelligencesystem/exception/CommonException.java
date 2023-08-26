package group.oneonetwo.hotelintelligencesystem.exception;

import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;

/**
 * @author 文
 * @description 保存/插入时异常
 */
public class CommonException extends RuntimeException {

    private Integer code;

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
        this.code = 5000;
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
