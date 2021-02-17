package cn.xjjdog.bcmall.module.pay.api;

import cn.xjjdog.bcmall.module.pay.domain.Payment;
import cn.xjjdog.bcmall.utils.utils.IdUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Service
public class PayApiImpl implements  PayApi{

    @Override
    public String createSimplePayment(String orderId, BigDecimal price) {
        Payment payment = Payment.createQuickPayment(orderId,price);
        //TODO
        return IdUtil.genCommonId();
    }
}

