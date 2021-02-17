package cn.xjjdog.bcmall.module.product.persistence.dao;

import cn.xjjdog.bcmall.module.product.persistence.StockHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface StockHistoryDao extends JpaRepository<StockHistoryEntity, String> {
    List<StockHistoryEntity> findAllBySkuId(String skuId);
}
