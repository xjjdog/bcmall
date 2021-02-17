package cn.xjjdog.bcmall.module.product.application;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class SingleStorageChangeCommand {
    @NotEmpty
    private String skuId;
    @Min(value = 1,message = "入库存储不能小于1")
    private int amount;
    @Min(value = 0,message = "入库价格不能小于0")
    private BigDecimal price;
    private String reason;
}

