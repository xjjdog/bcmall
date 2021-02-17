package cn.xjjdog.bcmall.auth;

import cn.xjjdog.bcmall.utils.utils.SpringContextUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 注意，我们的Filter并未受到Spring的管理,这是因为我们需要把它加入到过滤器链中。 <br/>
 * 如果被Spring管理，将会产生很多不可用预料的行为
 *
 * @author xjjdog
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    /**
     * 请勿直接引用，否则会绕过懒加载
     */
    private JwtTools jwt;

    /**
     * @return 懒加载获取JwtTools
     */
    private JwtTools getJwt() {
        if (null == jwt) {
            jwt = SpringContextUtil.getBean(JwtTools.class);
        }
        return jwt;
    }

    /**
     * 请求拦截过滤器实现。
     * 系统要求每一个受限资源，都需要提供一个名称叫做Authorization的Http头。当提供了错误的Token，或者没有提供Token，将会拒绝访问 <br/>
     * Token验证通过后，会在SecurityContextHolder中保持这些登录信息。由于SecurityContextHolder是ThreadLocal，所以在本次请求<br/>
     * 的生命周期中，可持续获取
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String token = request.getHeader("Authorization");
        if (!StringUtils.hasLength(token)) {
            log.debug("{} | request with empty token", request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        // 一般都会解析成功，除非瞎填，或者外部攻击
        Claims claims;
        try {
            claims = getJwt().getClaims(token);
        } catch (Exception ex) {
            log.error("JWT Token error: {} , cause: {}", token, ex.getMessage());
            chain.doFilter(request, response);
            return;
        }

        // 通过Token里的信息，构造验证信息，无需再与数据库进行交互; 这意味着，如果用户的权限变更，它需要重新登录
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            boolean ok = getJwt().validateTokenExpiration(claims);
            if (ok) {
                String name = claims.getSubject();
                List<GrantedAuthority> roles = (List<GrantedAuthority>) List.class.cast(claims.get("roles")).
                        stream()
                        .map(v -> new SimpleGrantedAuthority(String.valueOf(v)))
                        .collect(Collectors.toList());
                UserDetails userDetails = new User(name, "EMPTY", roles);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}
