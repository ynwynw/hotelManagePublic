package group.oneonetwo.hotelintelligencesystem.components.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 文
 * @description 没有携带token或者token无效
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    Logger logger = LoggerFactory.getLogger(Object.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        String reason = Reply.failed(String.valueOf(201),"用户未登录",null).toString();
        logger.error("code:{},msg:{},具体原因:{}", ResultCode.NO_LOGIN.getCode(),ResultCode.NO_LOGIN.getMsg(),e.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(Reply.failed(ResultCode.NO_LOGIN.getCode(),ResultCode.NO_LOGIN.getMsg(),null)));
    }
}
