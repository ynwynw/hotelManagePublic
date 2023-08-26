package group.oneonetwo.hotelintelligencesystem.components.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import group.oneonetwo.hotelintelligencesystem.components.security.exception.TokenIsExpiredException;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.JwtTokenUtils;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author 文
 * @description 鉴权过滤器
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


//    RedisUtil redisUtil;
    JwtTokenUtils jwtTokenUtils;

    Logger logger = LoggerFactory.getLogger(Object.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        //获取容器
        ServletContext context = request.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        jwtTokenUtils = ac.getBean(JwtTokenUtils.class);
        String tokenHeader = null;
        if ("/wsServer".equals(request.getRequestURI())) {
            tokenHeader = "Bearer " + request.getParameter("Authentication");
        } else {
            tokenHeader = request.getHeader(jwtTokenUtils.TOKEN_HEADER);
        }
        // 如果请求头中没有Authorization信息或token前缀不符合则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(jwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        try {
            //往security里写入认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        } catch (TokenIsExpiredException e) {
            logger.error(e.toString() + " : " + e.getMessage());
            //返回json形式的错误信息
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            String reason = Reply.failed("1040",e.getMessage(),null).toString();
            response.getWriter().write(new ObjectMapper().writeValueAsString(Reply.failed(ResultCode.TOKEN_EXPIRED.getCode(), e.getMessage(), null)));
            response.getWriter().flush();
            return;
        }
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws TokenIsExpiredException {
        String token = tokenHeader.replace(jwtTokenUtils.TOKEN_PREFIX, "").substring(1);
        //检查是否redis中存在传过来的token值,若不存在则过期
//        boolean expiration = redisUtil.hasKey(tokenHeader);
        boolean expiration = jwtTokenUtils.isExpiration(token);
        if (Boolean.TRUE.equals(expiration)) {
            throw new TokenIsExpiredException("token过期,请重新登录");
        } else {
            //获取token中的信息,并进行把用户角色告诉security进行鉴权操作
            String username = jwtTokenUtils.getUsername(token);
            String role = jwtTokenUtils.getUserRole(token);
            if (username != null) {
//                //刷新token时间
//                redisUtil.setKeyExpire(tokenHeader,1800000L);
                return new UsernamePasswordAuthenticationToken(username, null,
                        Collections.singleton(new SimpleGrantedAuthority(role))
                );
            }
        }
        return null;
    }
}
