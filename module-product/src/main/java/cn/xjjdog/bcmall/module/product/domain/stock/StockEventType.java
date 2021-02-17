package cn.xjjdog.bcmall.module.product.domain.stock;

import lombok.Getter;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 *
 * 入库是偶数，出库是奇数
 */
@Getter
public enum StockEventType {
    AdjustIn(0, 0, "调仓入库"),
    AdjustOut(0, 1, "调仓出库"),

    In(-1, 100, "正常入库"),
    Out(1, 101, "正常出库"),
    QuickIn(-1, 102, "快速入库"),
    ;

    private int mask = 1;
    private int value;
    private String description;

    StockEventType(int mask, int value, String description) {
        this.mask = mask;
        this.value = value;
        this.description = description;
    }

}
