package cn.xjjdog.bcmall.module.product.application;

import cn.xjjdog.bcmall.module.product.domain.meta.Brand;
import cn.xjjdog.bcmall.module.product.domain.meta.Category;
import cn.xjjdog.bcmall.module.product.domain.meta.Unit;
import cn.xjjdog.bcmall.module.product.domain.product.ProductState;
import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import cn.xjjdog.bcmall.module.product.domain.product.StockKeepingUnit;
import cn.xjjdog.bcmall.module.product.domain.product.SpecificationRelocateCommand;
import cn.xjjdog.bcmall.module.product.domain.stock.StockHistoryEvent;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface ProductService {
    Category readCategory(String id);

    Brand readBrand(String id);

    Unit readUnit(String id);

    /**
     * 读取商品SPU和它完整的信息
     *
     * @param spuId
     * @return
     */
    StandardProductUnit readSpu(String spuId);

    /**
     * 读取商品SKU和它完整的信息
     *
     * @param skuId
     * @return
     */
    StockKeepingUnit readSku(String skuId);

    /**
     * 保存当前的Spu
     *
     * @param spu
     */
    StandardProductUnit saveSpu(StandardProductUnit spu);

    /**
     * 根据skuId获取库存历史
     *
     * @param skuId
     * @return
     */
    List<StockHistoryEvent> findStockHistoryBySkuId(String skuId);

    /**
     * 转移库存
     *
     * @param spuId
     * @param fromId
     * @param toId
     * @param amount
     */
    void transfer(String spuId, String fromId, String toId, int amount, String reason);

    /**
     * 重新设计规格表
     *
     * @param spu
     * @param cmd
     * @return
     */
    boolean specificationRelocate(StandardProductUnit spu, SpecificationRelocateCommand cmd);

    /**
     * 入库
     *
     * @param request
     */
    void stockIn(SingleStorageChangeCommand request);

    void batchStockIn(List<SingleStorageChangeCommand> requests);

    /**
     * 出库
     * @param request
     */
    void stockOut(SingleStorageChangeCommand request);

    /**
     * 修改状态
     * @param spuId
     * @param state
     */
    void changeProductState(String spuId, ProductState state);
}
