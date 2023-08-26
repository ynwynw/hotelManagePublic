package group.oneonetwo.hotelintelligencesystem.components.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;


/**
 * @author 文
 * @description token工具类
 */
@Component
public class JwtTokenUtils {

    /**
     * 自定义标识
     */
    @Value("${jwt.header}")
    public String TOKEN_HEADER = "Authorization";

    /**
     * 前缀
     */
    @Value("${jwt.token-prefix}")
    public String TOKEN_PREFIX;

    /**
     * 密钥(基于base64)
     */
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.iss}")
    private static final String ISS = "tomato";

    // 角色的key
    @Value("${jwt.roleClaims}")
    private String ROLE_CLAIMS;

    //过期时间
    @Value("${jwt.expireTime}")
    private long EXPIRATION;

//    // 选择了记住我之后的过期时间为7天
//    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * 创建token
     * @author 文
     * @param uid
     * @param role
     * @return
     */
    public String createToken(String uid,String role) {
        HashMap<String, Object> map = new HashMap<>();
        //获取当前时间,以计算过期时间
        long time = System.currentTimeMillis();
//        long e = EXPIRATION;
//        long end = time + e;
//        System.out.println(new Date(time));
//        System.out.println(new Date(end));
        map.put(ROLE_CLAIMS, role);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(uid)
                .setIssuedAt(new Date())
                .setExpiration(new Date(time + EXPIRATION))
                .compact();
    }

    //

    /**
     * 从token中获取用户名(不需要头部)
     * @author 文
     * @param token
     * @return
     */
    public String getUsername(String token){
        return getTokenBody(token).getSubject();
    }



    /**
     * 获取用户角色
     * @author 文
     * @param token
     * @return
     */
    public String getUserRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }


    /**
     * 检查token是否过期
     * @author 文
     * @param token
     * @return
     */
    public boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 获得token体
     * @author 文
     * @param token
     * @return
     */
    private Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getTokenHeader() {
        return TOKEN_HEADER;
    }

    public String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public String getSECRET() {
        return SECRET;
    }

    public String getISS() {
        return ISS;
    }

    public String getRoleClaims() {
        return ROLE_CLAIMS;
    }

    public long getEXPIRATION() {
        return EXPIRATION;
    }

//    public static long getExpirationRemember() {
//        return EXPIRATION_REMEMBER;
//    }
}
