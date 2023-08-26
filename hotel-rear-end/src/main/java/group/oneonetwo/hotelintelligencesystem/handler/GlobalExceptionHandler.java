package group.oneonetwo.hotelintelligencesystem.handler;

import group.oneonetwo.hotelintelligencesystem.components.security.exception.TokenIsExpiredException;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 文
 * @description 全局异常捕捉
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    private static final String LOG_EXCEPTION_FORMAT = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    LogsService logsService;

    /**
     * 运行时异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Reply runtimeExceptionHandler(RuntimeException ex) {
        return resultFormat(ResultCode.RUNTIME_EX.getCode(), ex);
    }

    /**
     * 空指针异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Reply nullPointerExceptionHandler(NullPointerException ex) {
        log.error("NullPointerException : ");
        return resultFormat(ResultCode.NULL_POINT_EX.getCode(), ex);
    }

    /**
     * 类型转换异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public Reply classCastExceptionHandler(ClassCastException ex) {
        return resultFormat(ResultCode.CONVERT_EX.getCode(), ex);
    }

    /**
     * IO异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public Reply ioExceptionHandler(IOException ex) {
        return resultFormat(ResultCode.IO_EX.getCode(), ex);
    }

    /**
     * 未知方法异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public Reply noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return resultFormat(ResultCode.UNKNOWN_FUN_EX.getCode(), ex);
    }

    /**
     * 数组越界异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Reply indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return resultFormat(ResultCode.ARR_OUT_OF_BOUND_EX.getCode(), ex);
    }

    /**
     * 400错误:Http消息不可读异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Reply requestNotReadable(HttpMessageNotReadableException ex) {
        log.error("400 error -- requestNotReadable : ");
        return resultFormat(ResultCode.HTTP_MSG_CANNOT_READ_EX.getCode(), ex);
    }

    /**
     * 400错误:类型不匹配异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public Reply requestTypeMismatch(TypeMismatchException ex) {
        log.error("400 error -- TypeMismatchException : ");
        return resultFormat(ResultCode.TYPE_NOT_MATCH_EX.getCode(), ex);
    }

    /**
     * 400错误:缺少Servlet请求参数异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Reply requestMissingServletRequest(MissingServletRequestParameterException ex) {
        log.error("400 error -- MissingServletRequest : ");
        return resultFormat(ResultCode.SERVLET_PARAMETER_MISS_EX.getCode(), ex);
    }

    /**
     * 405错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Reply request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(ResultCode.HTTP_405_EX.getCode(), ex);
    }

    /**
     * 406错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public Reply request406(HttpMediaTypeNotAcceptableException ex) {
        log.error("406 error : ");
        return resultFormat(ResultCode.HTTP_406_EX.getCode(), ex);
    }

    /**
     * 500错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public Reply server500(RuntimeException ex) {
        log.error("500 error : ");
        return resultFormat(ResultCode.HTTP_500_EX.getCode(), ex);
    }

    /**
     * 栈溢出
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({StackOverflowError.class})
    public Reply requestStackOverflow(StackOverflowError ex) {
        return resultFormat(ResultCode.STACK_OVERFLOW_EX.getCode(), ex);
    }

    /**
     * 除数不能为0
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({ArithmeticException.class})
    public Reply arithmeticException(ArithmeticException ex){
        return resultFormat(ResultCode.DIVISOR_CANNOT_BE_ZERO_EX.getCode(), ex);
    }

    /**
     * 404错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({NotFoundException.class})
    public Reply notFindException(NotFoundException ex){
        return resultFormat(ResultCode.HTTP_404_EX.getCode(), ex);
    }


    /**
     * 其他系统级错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Reply exception(Exception ex) {
        return resultFormat(ResultCode.OTHER_EX.getCode(), ex);
    }

    /**
     * token过期异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({TokenIsExpiredException.class})
    public Reply tokenExpired(TokenIsExpiredException ex){
        return resultFormat(ResultCode.TOKEN_EXPIRED.getCode(), ex);
    }

    /**
     * 通用异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler({CommonException.class})
    public Reply commonException(CommonException ex) {
        return resultFormat(ex.getCode().toString(),ex);
    }

    /**
     * 捕捉结果格式化
     * @author 文
     * @param code
     * @param ex
     * @param <T>
     * @return
     */
    private <T extends Throwable> Reply resultFormat(String code, T ex) {
        ex.printStackTrace();
        log.error(String.format(LOG_EXCEPTION_FORMAT, code, ex.getMessage()));
        logsService.createLog(ex.getLocalizedMessage(),ex.toString(),3,0);
        return Reply.failed(String.valueOf(code), ex.getMessage(), null);
    }

}
