package cn.xjjdog.bcmall.module.order.application;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface OrderService {
    void createQuickOrder(QuickOrderCommand cmd);

    void closeOrder(CloseOrderCommand cmd);
}
