package cn.xjjdog.bcmall.module.product.persistence.dao;

import cn.xjjdog.bcmall.module.product.persistence.StockKeepingUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface StockKeepingUnitDao extends JpaRepository<StockKeepingUnitEntity, String> {
    @Query("from StockKeepingUnitEntity where spuId = ?1")
    List<StockKeepingUnitEntity> getSkus(String spuId);
}
