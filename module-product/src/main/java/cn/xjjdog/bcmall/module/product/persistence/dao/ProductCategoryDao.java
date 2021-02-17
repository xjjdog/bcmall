package cn.xjjdog.bcmall.module.product.persistence.dao;


import cn.xjjdog.bcmall.module.product.persistence.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategoryEntity, String> {
}
