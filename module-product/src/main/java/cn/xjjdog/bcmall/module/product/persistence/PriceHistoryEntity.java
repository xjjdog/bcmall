package cn.xjjdog.bcmall.module.product.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 价格历史表
 *
 * @author xjjdog
 */

@Entity
@Table(name = "base_product_price_history",
        indexes = {
                @Index(name = "idx_base_product_price_history_spuId", columnList = "spuId") // 根据spuId获取所有价格变更
        })
@Data
public class PriceHistoryEntity extends AbstractEntity {
    /**
     * SpuId
     */
    private String spuId;
    /**
     * SkuId
     */
    private String skuId;
    /**
     * 旧的价格
     */
    private BigDecimal oldPrice;
    /**
     * 新的价格
     */
    private BigDecimal price;
}

