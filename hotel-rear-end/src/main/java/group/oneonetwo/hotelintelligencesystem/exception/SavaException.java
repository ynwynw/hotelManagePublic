package group.oneonetwo.hotelintelligencesystem.exception;

/**
 * @author 文
 * @description 保存/插入时异常
 */
public class SavaException extends RuntimeException {

    public SavaException() {
    }

    public SavaException(String message) {
            super(message);
    }

    public SavaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavaException(Throwable cause) {
        super(cause);
    }

    public SavaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
