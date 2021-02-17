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
 * <p>
 * 品牌信息
 */
@Data
@Entity
@Table(name = "base_product_brand")
public class BrandEntity extends AbstractEntity {
    /**
     * 品牌名称
     */
    @NotBlank(message = "品牌信息不能为空")
    private String brandName;
    /**
     * logo
     */
    @NotBlank(message = "品牌logo不能为空")
    private String logo;


    /**
     * 品牌介绍
     */
    private String remark;
    /**
     * 产地
     */
    private String brandOrigin;
    /**
     * 品牌编码
     */
    private String brandCode;
}
