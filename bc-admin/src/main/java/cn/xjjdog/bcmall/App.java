package cn.xjjdog.bcmall;

import cn.xjjdog.bcmall.module.crm.persistence.MemberEntity;
import cn.xjjdog.bcmall.module.order.persistence.ExpressCompanyEntity;
import cn.xjjdog.bcmall.module.product.persistence.BrandEntity;
import cn.xjjdog.bcmall.module.product.persistence.ProductCategoryEntity;
import cn.xjjdog.bcmall.module.product.persistence.ProductUnitEntity;
import cn.xjjdog.bcmall.utils.quickdev.magicjpa.MagicJpaController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@SpringBootApplication
public class App {

    static {
        MagicJpaController.register("ProductCategory", ProductCategoryEntity.class);
        MagicJpaController.register("Brand", BrandEntity.class);
        MagicJpaController.register("ProductUnit", ProductUnitEntity.class);
        MagicJpaController.register("Member", MemberEntity.class);
        MagicJpaController.register("Express", ExpressCompanyEntity.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
