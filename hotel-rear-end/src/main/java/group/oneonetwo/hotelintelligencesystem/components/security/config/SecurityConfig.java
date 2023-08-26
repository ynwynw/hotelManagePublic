package group.oneonetwo.hotelintelligencesystem.components.security.config;


import group.oneonetwo.hotelintelligencesystem.components.security.exception.JWTAccessDeniedHandler;
import group.oneonetwo.hotelintelligencesystem.components.security.exception.JWTAuthenticationEntryPoint;
import group.oneonetwo.hotelintelligencesystem.components.security.filter.JWTAuthenticationFilter;
import group.oneonetwo.hotelintelligencesystem.components.security.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @author 文
 * @description security配置类
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用userDetailsService来进行账号密码验证
     * @author 文
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * security配置
     * @author 文
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 放行微信登录和完善用户名接口
//                .antMatchers("/auth/weChatLogin").permitAll()
//                .antMatchers("/auth/completeUsername").permitAll()
                //配置权限
//                .antMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")
//                .antMatchers("/proprietor/**").hasRole("ROLE_PROPRIETOR")
//                .antMatchers("/guard/**").hasRole("ROLE_GUARD")
//                .antMatchers("/management/**").hasRole("ROLE_MANAGEMENT")

                // 放行地址
                .antMatchers("/open/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui.html#").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()

                // 需要拦截验证的地址
                .anyRequest().authenticated()
//                .antMatchers("/tasks/**").authenticated()
//                .antMatchers("/proprietor/**").authenticated()
//                .antMatchers("/guard/**").authenticated()
//                .antMatchers("/management/**").authenticated()
//                .antMatchers("/public/**").authenticated()

                // 其他都放行了
//                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                //添加无权限时的处理
                .accessDeniedHandler(new JWTAccessDeniedHandler());
    }

    /**
     * 跨域拦截设置
     * @author 文
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        //开放哪些ip、端口、域名的访问权限，*表示开放所有域
        configuration.addAllowedOriginPattern("*");
        //允许HTTP请求中的携带哪些Header信息,*放行全部原始头信息
        configuration.addAllowedHeader("*");
        //开放哪些Http方法，允许跨域访问,*允许所有请求方法跨域调用
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        configuration.setExposedHeaders(Collections.singletonList(HttpHeaders.ACCEPT));
        configuration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", configuration);
        //使用默认跨域配置
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
