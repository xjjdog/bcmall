package cn.xjjdog.bcmall.module.product.domain.meta;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 品牌信息
 */
@Data
public class Brand extends BasicInfo {
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌介绍
     */
    private String remark;
    /**
     * 产地
     */
    private String brandOrigin;
    /**
     * 品牌编码
     */
    private String brandCode;
    /**
     * logo
     */
    private String logo;
}
