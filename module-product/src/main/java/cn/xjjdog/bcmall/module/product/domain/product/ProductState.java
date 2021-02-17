package cn.xjjdog.bcmall.module.product.domain.product;

import lombok.Getter;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Getter
public enum ProductState {
    Normal(1,"正常"),
    Dirty(102,"待调整"),
    DieOut(104,"淘汰"),
    ;


    private int value;
    private String description;

    ProductState(int value, String description) {
        this.value = value;
        this.description = description;
    }
}

