package cn.xjjdog.bcmall.module.order.domain;


import lombok.Getter;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Getter
public enum OrderState {
    NewOrder(1, "新建，未确认"),
    Payed(2, "已经支付"),
    Refund(3, "退款"),
    Delivery(4, "已发货"),
    Closed(5, "正常关闭"),
    FalseClosed(6, "非正常关闭 : 取消，超时等");

    private int value;
    private String description;

    OrderState(int value, String description) {
        this.value = value;
        this.description = description;
    }
}

