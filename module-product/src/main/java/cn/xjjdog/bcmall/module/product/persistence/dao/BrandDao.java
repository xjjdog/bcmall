package cn.xjjdog.bcmall.module.product.persistence.dao;

import cn.xjjdog.bcmall.module.product.persistence.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface BrandDao extends JpaRepository<BrandEntity, String> {
}
