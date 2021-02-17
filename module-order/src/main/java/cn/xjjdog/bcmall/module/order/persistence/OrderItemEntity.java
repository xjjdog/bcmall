package cn.xjjdog.bcmall.module.order.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Entity
@Table(name = "order_item",
        indexes = {
                @Index(name = "idx_order_item_orderId", columnList = "orderId"),
                @Index(name = "idx_order_item_spuId_skuId", columnList = "spuId"),
                @Index(name = "idx_order_item_spuId_skuId", columnList = "skuId"),
        })
@Data
public class OrderItemEntity extends AbstractEntity {
    /**
     * 父订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private String itemState;
    /**
     * SPU信息
     */
    private String spuId;
    /**
     * SKU信息
     */
    private String skuId;
    /**
     * SKU快照，为了保存订单发生时的状态，我们保存此处的快照
     */
    @Column(length = 1024)
    private String skuInfo;
    /**
     * 实付价格，因为会有会员卡打折或者其他优惠券参与，用于退款等事项
     */
    private BigDecimal realPrice;
    /**
     * 购买数量
     */
    private int amount;
    /**
     * 价格
     */
    private BigDecimal price;
}

