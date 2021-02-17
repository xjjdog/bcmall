package cn.xjjdog.bcmall.module.product.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 一般公司部门已经规定禁止Mysql sql 语句使用like 关键字，但单机还是不可避免
 *
 * @author xjjdog
 * 商品
 */
@Data
@Entity
@Table(name = "base_spu",
        indexes = {
                @Index(name = "idx_base_spu_shortName", columnList = "shortName")
        })
public class StandardProductUnitEntity extends AbstractEntity {
    /**
     * 商品简短的名称
     */
    @NotBlank
    private String shortName;
    /**
     * 规格配置表
     */
    private String specDefined;
    /**
     * 商品公共属性
     */
    private String attributes;
    /**
     * 商品状态
     */
    private Integer state;
    /**
     * 分类信息
     */
    @NotBlank
    private String categoryId;
    /**
     * 保存品牌关系
     */
    private String brandId;
    /**
     * 保存单位关系
     */
    @NotNull
    private String unitId;
    /**
     * 使用，分割的图像列表
     */
    @Column(length = 512)
    private String photos;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 是否热门
     */
    private boolean flagHot = false;
    /**
     * 是否新品
     */
    private boolean flagNew = false;
    /**
     * 是否推荐
     */
    private boolean flagRecommend = false;
    /**
     * 最小价格
     */
    private BigDecimal minPrice;
    /**
     * 最大价格
     */
    private BigDecimal maxPrice;

}

