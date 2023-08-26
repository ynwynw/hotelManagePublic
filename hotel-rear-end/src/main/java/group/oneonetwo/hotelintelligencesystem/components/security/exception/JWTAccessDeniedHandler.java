package group.oneonetwo.hotelintelligencesystem.components.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 文
 * @description 没有访问权限
 */
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    Logger logger = LoggerFactory.getLogger(Object.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        String reason = Reply.failed(String.valueOf(204),"无权访问",null).toString();
        logger.error("code:{},msg:{},具体原因:{}",ResultCode.USER_NO_ACCESS.getCode(),ResultCode.USER_NO_ACCESS.getMsg(),e.getMessage());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Reply.failed(ResultCode.USER_NO_ACCESS.getCode(),ResultCode.USER_NO_ACCESS.getMsg(),null)));
    }
}
