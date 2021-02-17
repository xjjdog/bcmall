package cn.xjjdog.bcmall.auth.request;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 登录请求
 *
 * @author xjjdog
 */
@Data
public class LoginRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 验证类型：暂不启用
     */
    private String type;
}
