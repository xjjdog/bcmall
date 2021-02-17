package cn.xjjdog.bcmall.module.product.application;

import cn.xjjdog.bcmall.module.product.domain.meta.Brand;
import cn.xjjdog.bcmall.module.product.domain.meta.Category;
import cn.xjjdog.bcmall.module.product.domain.meta.Unit;
import cn.xjjdog.bcmall.module.product.domain.product.ProductState;
import cn.xjjdog.bcmall.module.product.domain.product.SpecificationRelocateCommand;
import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import cn.xjjdog.bcmall.module.product.domain.product.StockKeepingUnit;
import cn.xjjdog.bcmall.module.product.domain.stock.StockEventType;
import cn.xjjdog.bcmall.module.product.domain.stock.StockHistoryEvent;
import cn.xjjdog.bcmall.module.product.persistence.*;
import cn.xjjdog.bcmall.module.product.persistence.dao.PriceHistoryDao;
import cn.xjjdog.bcmall.module.product.persistence.dao.StandardProductUnitDao;
import cn.xjjdog.bcmall.module.product.persistence.dao.StockHistoryDao;
import cn.xjjdog.bcmall.module.product.persistence.dao.StockKeepingUnitDao;
import cn.xjjdog.bcmall.module.product.util.Transform;
import cn.xjjdog.bcmall.utils.quickdev.magicjpa.MagicJpaService;
import cn.xjjdog.bcmall.utils.utils.IdUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final StockKeepingUnitDao skuDao;
    private final StandardProductUnitDao spuDao;
    private final MagicJpaService adaption;
    private final StockHistoryDao stockHistoryDao;
    private final PriceHistoryDao priceHistoryDao;

    private void readSpuDependency(StandardProductUnitEntity spuE, StandardProductUnit spu) {
        if (null != spuE.getBrandId()) {
            BrandEntity brand = adaption.read(BrandEntity.class, spuE.getBrandId());
            spu.setBrand(Transform.T.fromBrandEntity(brand));
        }

        ProductUnitEntity unit = adaption.read(ProductUnitEntity.class, spuE.getUnitId());
        ProductCategoryEntity category = adaption.read(ProductCategoryEntity.class, spuE.getCategoryId());

        spu.setUnit(Transform.T.fromUnitEntity(unit));
        spu.setCategory(Transform.T.fromCategoryEntity(category));
    }

    @Override
    public Category readCategory(String id) {
        ProductCategoryEntity category = adaption.read(ProductCategoryEntity.class, id);
        return Optional.ofNullable(category).map(b -> Transform.T.fromCategoryEntity(b)).orElse(null);
    }

    @Override
    public Brand readBrand(String id) {
        BrandEntity brand = adaption.read(BrandEntity.class, id);
        return Optional.ofNullable(brand).map(b -> Transform.T.fromBrandEntity(b)).orElse(null);
    }

    @Override
    public Unit readUnit(String id) {
        ProductUnitEntity unit = adaption.read(ProductUnitEntity.class, id);
        return Optional.ofNullable(unit).map(b -> Transform.T.fromUnitEntity(b)).orElse(null);
    }

    @Override
    public StandardProductUnit readSpu(String spuId) {
        StandardProductUnitEntity spuE = spuDao.findById(spuId).orElse(null);
        List<StockKeepingUnitEntity> skuEs = skuDao.getSkus(spuId);
        StandardProductUnit spu = Transform.T.fromSpuEntity(spuE);
        readSpuDependency(spuE, spu);

        List<StockKeepingUnit> skus = Transform.T.fromSkuEntityList(skuEs);
        skus.forEach(s -> s.setSpuId(spuId));
        spu.setSkus(skus);

        return spu;
    }

    @Override
    public StockKeepingUnit readSku(String id) {
        StockKeepingUnitEntity skuE = skuDao.findById(id).get();
        StockKeepingUnit sku = Transform.T.fromSkuEntity(skuE);
        return sku;
    }

    @Override
    @Transactional
    public StandardProductUnit saveSpu(StandardProductUnit spu) {

        StandardProductUnitEntity spuE = Transform.T.fromSpu(spu);
        List<StockKeepingUnitEntity> skuEs = Transform.T.fromSkuList(spu.getSkus());
        skuDao.saveAll(skuEs);

        StandardProductUnitEntity savedSpu = spuDao.save(spuE);
        spu.setId(savedSpu.getId());

        return spu;
    }

    /**
     * 保存当前的SKU
     *
     * @param sku
     */
    private StockKeepingUnit saveSku(StockKeepingUnit sku) {
        StockKeepingUnitEntity e = skuDao.save(Transform.T.fromSku(sku));
        sku.setId(e.getId());
        return sku;
    }

    /**
     * 一次性保存多个SKU
     * 主要是，可以把它们放在事务中
     *
     * @param skus
     */
    @Transactional
    public void saveSku(List<StockKeepingUnit> skus) {
        List<StockKeepingUnitEntity> skuEs = Transform.T.fromSkuList(skus);
        skuDao.saveAll(skuEs);
    }

    @Override
    public List<StockHistoryEvent> findStockHistoryBySkuId(String skuId) {
        List<StockHistoryEntity> list = stockHistoryDao.findAllBySkuId(skuId);
        return list.stream()
                .map(h -> Transform.T.fromStockHistoryEntity(h))
                .collect(Collectors.toList());
    }

    @Override
    public void transfer(String spuId, String fromId, String toId, int amount, String reason) {
        StandardProductUnit spu = readSpu(spuId);
        spu.transfer(fromId, toId, amount);
        saveSpu(spu);

        StockKeepingUnit from = spu.findSkuById(fromId);
        StockKeepingUnit to = spu.findSkuById(toId);
        //调仓，不会产生价格变化
        saveStorageHis(StockEventType.AdjustIn, spuId, fromId, from.getStorage(), BigDecimal.ZERO, amount, reason);
        saveStorageHis(StockEventType.AdjustOut, spuId, toId, to.getStorage(), BigDecimal.ZERO, amount, reason);
    }

    @Override
    @Transactional
    public boolean specificationRelocate(StandardProductUnit spu, SpecificationRelocateCommand cmd) {
        List<PriceHistoryEntity> history = Lists.newArrayList();

        if (!StringUtils.hasLength(spu.getId())) {
            spu.setState(ProductState.Normal);
            spu.setId(IdUtil.genCommonId());
        } else {
            for (SpecificationRelocateCommand.SpecificationRelocateItem item : cmd.getItems()) {
                StockKeepingUnit oldSku = spu.findSkuByFlatSpecKey(item.getSpecFlatKey());
                if (oldSku != null && 0 != item.getPrice().compareTo(oldSku.getPrice())) {
                    PriceHistoryEntity historyEntity = new PriceHistoryEntity();
                    historyEntity.setPrice(item.getPrice());
                    historyEntity.setOldPrice(oldSku.getPrice());
                    historyEntity.setSkuId(oldSku.getId());
                    historyEntity.setSpuId(spu.getId());
                    history.add(historyEntity);
                }
            }
            priceHistoryDao.saveAll(history);
        }
        boolean ok = spu.specificationRelocate(cmd);
        saveSpu(spu);
        return ok;
    }

    /**
     * 入库
     *
     * @param request
     */
    @Override
    @Transactional
    public void stockIn(SingleStorageChangeCommand request) {
        int amount = request.getAmount();
        BigDecimal price = request.getPrice();
        String skuId = request.getSkuId();
        String reason = request.getReason();

        StockKeepingUnitEntity sku = skuDao.findById(skuId).orElse(null);
        Preconditions.checkNotNull(sku, "未找到这个商品" + skuId);

        int storage = sku.getStorage();
        sku.setStorage(storage + amount);
        skuDao.save(sku);
        saveStorageHis(StockEventType.In, sku.getSpuId(), skuId, storage, price, amount, reason);
    }

    @Override
    @Transactional
    public void batchStockIn(List<SingleStorageChangeCommand> requests) {
        for (SingleStorageChangeCommand request : requests) {
            this.stockIn(request);
        }
    }


    @Override
    @Transactional
    public void stockOut(SingleStorageChangeCommand request) {
        int amount = request.getAmount();
        BigDecimal price = request.getPrice();
        String skuId = request.getSkuId();
        String reason = request.getReason();

        Preconditions.checkArgument(amount > 0, "出库数量必须大于0");

        StockKeepingUnitEntity sku = skuDao.findById(skuId).orElse(null);
        Preconditions.checkNotNull(sku, "未找到这个商品" + skuId);

        int storage = sku.getStorage();

        Preconditions.checkState(storage >= amount, "库存数量不足");

        sku.setStorage(storage - amount);
        skuDao.save(sku);
        saveStorageHis(StockEventType.Out, sku.getSpuId(), skuId, storage, price, amount, reason);
    }

    /**
     * 修改状态
     *
     * @param spuId
     * @param state
     */
    @Override
    @Transactional
    public void changeProductState(String spuId, ProductState state) {
        StandardProductUnit spu = readSpu(spuId);
        if (spu.getState() == state) {
            return;
        }
        Preconditions.checkState(spu.getState() != ProductState.Dirty, "商品需要调整后才能操作");

        spu.setState(state);
        saveSpu(spu);
    }

    /**
     * 生成库存历史
     */
    private StockHistoryEvent saveStorageHis(StockEventType eventType,
                                             String spuId,
                                             String skuId,
                                             int storage,
                                             BigDecimal price,
                                             int amount,
                                             String reason) {
        BigDecimal subtotal = price.multiply(new BigDecimal(amount * eventType.getMask()));
        StockHistoryEvent event = new StockHistoryEvent();
        event.setEventType(eventType);
        event.setSkuId(skuId);
        event.setSpuId(spuId);
        event.setPrice(price);
        event.setAmount(amount);
        event.setSnapshotStock(storage);
        event.setSubtotal(subtotal);
        event.setReason(reason);


        StockHistoryEntity historyEntity = Transform.T.fromStockHistory(event);
        this.stockHistoryDao.save(historyEntity);

        return event;
    }
}
