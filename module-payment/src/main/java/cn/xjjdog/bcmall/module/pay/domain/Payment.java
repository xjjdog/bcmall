package cn.xjjdog.bcmall.module.pay.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 支付信息
 */
@Data
public class Payment {
    private String orderId;
    private BigDecimal totalPrice;
    private List<PaymentItem> payments;


    /**
     * 创建简单的支付信息
     *
     * @return
     */
    public static Payment createQuickPayment(String orderId, BigDecimal price) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);

        PaymentItem paymentItem = new PaymentItem();
        paymentItem.setChannel(PaymentChannelType.FaceToFace);
        paymentItem.setPartPrice(price);
        payment.setPayments(Lists.newArrayList(paymentItem));
        payment.setTotalPrice(price);

        return payment;
    }
}
