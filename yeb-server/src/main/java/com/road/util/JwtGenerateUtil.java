package com.road.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: zhouc
 * @Date: 2021/9/6 22:13
 * @Since： 1.0
 * @Description: JWT工具类
 */
@Component
public class JwtGenerateUtil {

    @Value("${jwt.secret}")
    private String secret; // 密钥
    @Value("${jwt.expiration}")
    private Long expiration; // 过期时间

    // 常量信息头
    public static final String TOKEN_USERNAME = "sub";
    public static final String TOKEN_CREATED = "created";

    /**
     * 根据用户信息生成Token
     * @param userDetails
     */
    public String generateToken(UserDetails userDetails){
        // Claim 记住不能存放用户敏感信息
        HashMap<String, Object> claim = new HashMap<>();
        claim.put(TOKEN_USERNAME, userDetails.getUsername());
        claim.put(TOKEN_CREATED, new Date());
        return generateToken(claim);
    }

    /**
     * 根据Claim生成Token
     * @param claim
     * @return
     */
    private String generateToken(HashMap<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateTokenExpiration())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 生成Token过期时间
     * @return {date}
     */
    private Date generateTokenExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 根据Token获得用户名
     * @param token token
     * @return {username}
     */
    public String getTokenUsername(String token){
        String username = null;
        try {
            username = getClaimsInToken(token).getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 获得Token中的Claims
     * @return {Claims}
     */
    private Claims getClaimsInToken(String token){
        return Jwts.parser()
                .setSigningKey(secret) // 放入密钥解密
                .parseClaimsJws(token) // 解析Token
                .getBody();// 获得解析后的值
    }

    /**
     * 判断Token是否过期
     * @param token
     * @return {boolean}
     */
    public boolean isTokenExpired(String token){
        return getTokenExpiration(token).before(new Date(expiration));
    }

    /**
     * 获得Token的过期时间
     * @param token token
     * @return {date}
     */
    public Date getTokenExpiration(String token){
        return getClaimsInToken(token).getExpiration();
    }

    /**
     * 刷新Token
     * @param
     * @return
     */
    public String refreshToken(String token,UserDetails userDetails){
        // 判断是否满足刷新条件
       if (isTokenCanBeRefresh(token, userDetails)){
           return generateToken(userDetails);
       }
        return token;
    }

    /**
     * 根据过期时间以及有效字段username判断token是否无效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenCanBeRefresh(String token,UserDetails userDetails) {
        return !isTokenExpired(token) || getTokenUsername(token).equals(userDetails.getUsername());
    }
}
