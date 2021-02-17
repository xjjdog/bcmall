package cn.xjjdog.bcmall.module.order.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Entity
@Table(name = "order_express_company")
@Data
public class ExpressCompanyEntity extends AbstractEntity {
    private String companyName;
    private String logo;
    private String expressCode;
    private String homePage;
    private String tel;
    private String searchUrl;
}

