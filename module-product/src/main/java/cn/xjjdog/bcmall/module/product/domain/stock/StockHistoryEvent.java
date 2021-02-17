package cn.xjjdog.bcmall.module.product.domain.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 库存概念
 * 这里只存储库存数量，后续可能会根据地域划分库存、根据分店设置库存
 */
@Data
public class StockHistoryEvent {

    private StockEventType eventType;
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
    private int amount;
    /**
     * 当前库存快照
     */
    private int snapshotStock;
    /**
     * 入库，或者出库价格
     */
    private BigDecimal price;
    /**
     * 原因
     */
    private String reason;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间：和创建时间一致，不允许修改
     */
    private Date lastModifiedDate;
    /**
     * 小计
     */
    private BigDecimal subtotal;
}
