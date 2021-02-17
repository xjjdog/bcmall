package cn.xjjdog.bcmall.auth;

import cn.xjjdog.bcmall.auth.request.LoginRequest;
import cn.xjjdog.bcmall.auth.request.RenewRequest;
import cn.xjjdog.bcmall.auth.response.Token;
import cn.xjjdog.bcmall.auth.response.UserInfo;
import cn.xjjdog.bcmall.utils.web.Result;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 用户验证接口
 *
 * @author xjjdog
 */
@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
@Api("用户验证接口")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTools jwt;

    @ApiOperation("获取当前用户信息")
    @GetMapping("currentUser")
    public UserInfo currentUser() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar("http://xjjdog.cn/pkq.jpeg");
        userInfo.setName("小姐姐味道");
        return userInfo;
    }

    @ApiOperation("令牌续租接口")
    @PostMapping("renewToken")
    public Result<?> renew(@RequestBody RenewRequest renewReq) {
        String newToken = jwt.renew(renewReq.getOldToken());
        return Result.of(new Token(newToken));
    }


    @ApiOperation("获取令牌")
    @PostMapping("getToken")
    public Result<?> login(@RequestBody LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();

        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);

        Authentication authentication = authenticate(username, password);
        User user = User.class.cast(authentication.getPrincipal());

        List<String> roles = user.getAuthorities()
                .stream()
                .map(v -> v.getAuthority())
                .collect(Collectors.toList());

        Map map = new HashMap<>();
        map.put("roles", roles);

        final String token = jwt.generateToken(map, username);

        return Result.of(new Token(token));
    }

    /**
     * 调用密码验证方法，这里使用Spring Security的用户管理方法
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 验证后的 Authentication
     */
    private Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
