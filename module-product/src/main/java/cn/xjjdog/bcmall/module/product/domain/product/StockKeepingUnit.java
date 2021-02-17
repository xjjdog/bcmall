package cn.xjjdog.bcmall.module.product.domain.product;

import cn.xjjdog.bcmall.utils.BasicInfo;
import cn.xjjdog.bcmall.utils.utils.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 商品详情
 */
@Getter
@Setter
@ToString(exclude = "spu")
public class StockKeepingUnit extends BasicInfo {

    private ProductState state = ProductState.Normal;

    /**
     * SPU引用
     */
    private String spuId;
    /**
     * 根据规格而平铺开的Key，这个Key，其实是一个自然排序的JSON串
     */
    private String specFlatKey;
    /**
     * 规格条码：SKU码
     */
    private String specCode;
    /**
     * 69开头的13位标准码
     */
    private String barcode;
    /**
     * 库存数量，只允许从入库进行调整，不允许设置
     * 这是非常重要的一个属性，用于标识是否修改
     */
    private int storage;
    /**
     * 建议零售价。单位：分
     */
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
    private String photos;
    /**
     * 缩略图
     */
    private String thumbnail;

    private int keepStorage;
    private int warnStorage;
    /**
     * 根据FlatKey，获取对应的Map
     *
     * @return
     */
    public Map<String, String> fromFlatKey() {
        return Try.of(() -> JSON.OM.readValue(getSpecFlatKey(), TreeMap.class))
                .getOrElse(Maps.newTreeMap());
    }

    /**
     * 转移库存，将库存转移到目标SKU之上
     * <p>
     * 一般发生在：SPU添加，或者删除了规格；或者进行调仓配置
     *
     * @param to     目标SKU
     * @param amount 转移的库存数量
     * @return
     */
    public void transfer(StockKeepingUnit to, int amount) {
        Preconditions.checkArgument(amount <= storage, "库存数量不足，转储失败");

        to.setStorage(to.getStorage() + amount);
        this.storage = storage - amount;
    }
}
