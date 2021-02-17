package cn.xjjdog.bcmall.module.product.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 商品单位
 */
@Data
@Entity
@Table(name = "base_product_unit")
public class ProductUnitEntity extends AbstractEntity {
    /**
     * 显示名称，比如克、件
     */
    @NotBlank
    private String unitName;
    /**
     * 1、计件
     * 2、计重
     * 3、记时
     */
    @NotNull
    private Integer measureType;
    /**
     * 排序号
     */
    private Integer sortNum;
}
