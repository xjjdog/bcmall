package cn.xjjdog.bcmall.module.product.persistence.dao;


import cn.xjjdog.bcmall.module.product.persistence.ProductUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface ProductUnitDao extends JpaRepository<ProductUnitEntity, String> {
}
