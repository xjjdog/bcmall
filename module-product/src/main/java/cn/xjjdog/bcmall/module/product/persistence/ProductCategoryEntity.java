package cn.xjjdog.bcmall.module.product.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
@Entity
@Table(name = "base_product_category")
public class ProductCategoryEntity extends AbstractEntity {
    /**
     * 层级
     */
    private String path;
    /**
     * 父类ID
     */
    private String parentId;
    /**
     * 排序号
     */
    private Integer seq;
    /**
     * 分类名称
     */
    @NotBlank
    private String categoryName;
    /**
     * 分类介绍
     */
    private String remark;
}
