package cn.xjjdog.bcmall.module.product.application;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Data
public class TransferCommand {
    @NotEmpty
    private String spuId;
    @NotEmpty
    private String fromSku;
    @NotEmpty
    private String toSku;
    @Min(1)
    private int amount;
    private String reason;
}

