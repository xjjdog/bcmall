package cn.xjjdog.bcmall.module.product.domain.meta;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class Category extends BasicInfo {
    /**
     * 层级
     */
    private String path;
    /**
     * 父类ID
     */
    private String parentId;
    /**
     * 排序号
     */
    private int seq;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类介绍
     */
    private String remark;
}
