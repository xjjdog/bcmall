package cn.xjjdog.bcmall.auth.response;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 请求成功返回令牌
 *
 * @author xjjdog
 */
@Data
public class Token {
    /**
     * JWT令牌
     */
    private final String token;
}
