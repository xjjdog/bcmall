package cn.xjjdog.bcmall.module.product.controller.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class BatchStockInFormRequest {
    @Data
    public static class Item{
        @Min(value = 1,message = "入库数量必须大于0")
        int amount;
        @Min(value = 0,message = "入库价格不能小于0")
        BigDecimal price;

        String skuInfo;
    }
    String reason;

    @NotEmpty(message = "必须提供要入库的商品")
    @Valid
    List<Item> items;
}

