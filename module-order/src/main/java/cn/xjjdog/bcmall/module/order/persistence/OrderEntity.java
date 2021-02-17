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
 * <p>
 * 汇总订单，仅供展示用。一旦支付和物流发生拆分，它将裂变成两个订单
 */

@Entity
@Table(name = "order_record",
        indexes = {
                @Index(name = "idx_order_record_memberId", columnList = "memberId"),
        })
@Data
public class OrderEntity extends AbstractEntity {
    /**
     * 会员ID，未知则为0
     */
    private String memberId = "0";
    /**
     * 会员信息快照
     */
    @Column(length = 1024)
    private String memberInfo;
    /**
     * 商品快照
     */
    @Column(length = 1024)
    private String skusInfo;
    /**
     * 发生金额
     */
    private BigDecimal totalPrice;
    /**
     * 优惠金额
     */
    private BigDecimal discount;
    /**
     * 是否已经付费
     */
    private String remark;

    /**
     * 订单状态
     */
    private int state;
}

