package cn.xjjdog.bcmall.module.pay.api;

import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface PayApi {
    String createSimplePayment(String orderId, BigDecimal price);
}
