package cn.xjjdog.bcmall.auth.response;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 用户基本
 *
 * @author xjjdog
 */

@Data
public class UserInfo {
    private String avatar;
    private String name;
    private String title;
    private String group;
    private String signature;
    private String userid;
}

