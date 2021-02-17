package cn.xjjdog.bcmall.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 这里是JWT主要的验证类，使用io.jsonwebtoken类进行API设计
 *
 * @author xjjdog
 */
@Component
public class JwtTools {

    /**
     * 混淆的密钥，必须是Base64格式; 注意需要修改成自己的密钥，否则token信息能够被伪造
     */
    @Value("${bcmall.jwt.secret}")
    private String secret = "bGsyMzRqbGs4MDIzNGxzZOWPr+i/nuaOpeWFi+mHjOaWr+acteWkq2lzb2Zpb3MyM3U4NDMybmRzZGZzb2tqampza2xmanNsayVeJl4mJSQjJCQlIzgzIDEyPTEyeTN1aXV5Jl4=";


    /**
     * Token失效的时间，单位是秒，默认是1天
     */
    @Value("${bcmall.jwt.expire}")
    private int expire = 24 * 60 * 60;


    /**
     * 令牌续租
     *
     * @param oldToken 旧令牌
     * @return 新令牌
     */
    public String renew(String oldToken) {
        Claims claims = getClaims(oldToken);
        return generateToken(claims, claims.getSubject());
    }

    /**
     * 构造Token
     *
     * @param claims  令牌信息
     * @param subject 用户主体
     * @return
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        Date now = Calendar.getInstance().getTime();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证令牌是否已经过期
     *
     * @param claims 令牌信息
     * @return 是否已经过期
     */
    public Boolean validateTokenExpiration(Claims claims) {
        final Date expiration = claims.getExpiration();
        return expiration.after(Calendar.getInstance().getTime());
    }

    /**
     * 根据字符串令牌，解析其中的内容
     *
     * @param token 原始令牌
     * @return 解析后的令牌信息
     */
    public Claims getClaims(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
