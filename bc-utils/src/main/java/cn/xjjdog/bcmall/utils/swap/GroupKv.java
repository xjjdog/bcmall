package cn.xjjdog.bcmall.utils.swap;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 用来交换Group数据
 */
@Data
public class GroupKv {
    public GroupKv(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    /**
     * 分组名称
     */
    private String name;
    /**
     * 分组数量
     */
    private Long count;
}
