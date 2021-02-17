package cn.xjjdog.bcmall.module.product.controller.request;

import cn.xjjdog.bcmall.module.product.domain.product.ProductState;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class ChangeProductStateRequest {
    @NotBlank
    private String spuId;
    @NotNull
    private ProductState state;
}

