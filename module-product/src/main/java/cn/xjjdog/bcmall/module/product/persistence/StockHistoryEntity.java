package cn.xjjdog.bcmall.module.product.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 库存历史
 */
@Data
@Entity
@Table(name = "stock_history",
        indexes = {
                @Index(name = "idx_stock_history_spuId", columnList = "spuId")
        })
public class StockHistoryEntity extends AbstractEntity {
    /**
     * 库存历史类型
     */
    private Integer eventType;
    /**
     * SPU 关联
     */
    private String spuId;
    /**
     * SKU 关联
     */
    private String skuId;
    /**
     * 库存调整数量：正数为入库，负数为出库
     */
    private Integer amount;
    /**
     * 当前库存快照
     */
    private Integer snapshotStock;
    /**
     * 原因
     */
    private String reason;
    /**
     * 入库，或者出库价格
     */
    private BigDecimal price;
    /**
     * 小计
     */
    private BigDecimal subtotal;
}