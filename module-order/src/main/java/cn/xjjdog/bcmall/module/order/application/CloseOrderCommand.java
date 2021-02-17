package cn.xjjdog.bcmall.module.order.application;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class CloseOrderCommand {
    @NotBlank
    private String orderId;
    private boolean normal;
    private String remark;
}

