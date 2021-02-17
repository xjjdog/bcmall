package cn.xjjdog.bcmall.module.order.domain;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 *
 * 物流公司信息
 */
@Data
public class ExpressCompany extends BasicInfo {
    private String companyName;
    private String logo;
    private String homePage;
    private String tel;
    private String searchUrl;
}

