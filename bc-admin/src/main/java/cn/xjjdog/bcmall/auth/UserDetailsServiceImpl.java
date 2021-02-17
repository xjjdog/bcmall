package cn.xjjdog.bcmall.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 主要的用户口令验证类，注意我们这里使用的是BCrypt格式的密码，安全性更高
 *
 * @author xjjdog
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 已经在 WebSecurityConfig 中生成
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 构造用户基本信息，需要取出用户的加密密码和权限列表。这些信息验证通过后，将一次性放入令牌中
     *
     * @param username 用户名称
     * @return 用户基本信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, mockPassword(), getAuthorities(username));
    }

    /**
     * 快速测试用，只要密码是123456就返回
     * TODO 修改为数据库验证
     */
    private String mockPassword() {
        return passwordEncoder.encode("123456");
    }

    /**
     * 获取用户权限信息
     *
     * @param username 用户名
     * @return 用户权限信息
     */
    private Collection<GrantedAuthority> getAuthorities(String username) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_DEMO"));
        if (username.equals("admin")) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authList;
    }
}
