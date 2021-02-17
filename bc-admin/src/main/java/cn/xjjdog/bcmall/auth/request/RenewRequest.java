package cn.xjjdog.bcmall.auth.request;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 令牌刷新
 *
 * @author xjjdog
 */
@Data
public class RenewRequest {
    /**
     * 旧的令牌
     */
    private String oldToken;
}
