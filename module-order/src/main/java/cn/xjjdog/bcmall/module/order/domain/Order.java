package cn.xjjdog.bcmall.module.order.domain;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 订单明细
 */
@Data
public class Order extends BasicInfo {
    /**
     * 会员ID，未知则为0
     */
    private String memberId = "0";
    /**
     * 会员信息快照
     */
    private String memberInfo;
    /**
     * 商品快照
     */
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
     * 关闭备注
     */
    private String remark;

    /**
     *  订单状态
     */
    private OrderState state;
    /**
     * 物流
     */
    private Logistics logistics;
    private List<OrderItem> items;

}
