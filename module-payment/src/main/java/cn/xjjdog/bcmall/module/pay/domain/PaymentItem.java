package cn.xjjdog.bcmall.module.pay.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentItem {
    /**
     * 这一部分的付费信息
     */
    private BigDecimal partPrice;
    /**
     * 付费渠道
     */
    private PaymentChannelType channel;
    /**
     * 付费流水
     */
    private String payId;
}
