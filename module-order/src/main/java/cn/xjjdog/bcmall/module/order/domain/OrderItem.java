package cn.xjjdog.bcmall.module.order.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 订单项
 */
@Data
public class OrderItem {
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
     * 价格快照
     */
    private BigDecimal price;
}
