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
 *
 * @author xjjdog
 * 商品规格，这才是真正要售卖的商品
 */
@Data
@Entity
@Table(name = "base_sku",
        indexes = {
                @Index(name = "idx_base_sku_spuId", columnList = "spuId")
        })
public class StockKeepingUnitEntity extends AbstractEntity {
    /**
     * 状态
     */
    private Integer state;
    /**
     * 69开头的13位标准码
     */
    private String barcode;
    /**
     * SpuId
     */
    @NotBlank
    private String spuId;
    /**
     * 根据规格而平铺开的Key，这个Key，其实是一个自然排序的JSON串
     */
    @NotBlank
    private String specFlatKey;
    /**
     * 规格条码：保存时自动创建
     */
    private String specCode;
    /**
     * 库存数量，只允许从入库进行调整，不允许设置
     * 这是非常重要的一个属性，用于标识是否修改
     */
    private Integer storage;
    /**
     * 建议零售价。单位：分
     */
    @NotNull
    private BigDecimal price;
    /**
     * 给一串附加的标题
     */
    private String specLabel;
    /**
     * 备忘标记
     */
    private String memo;
    /**
     * 使用，分割的图像列表
     */
    @Column(length = 512)
    private String photos;
    /**
     * 缩略图
     */
    @NotBlank
    private String thumbnail;
    /**
     * 保留库存
     */
    private Integer keepStorage;
    /**
     * 库存预警
     */
    private Integer warnStorage;
}
