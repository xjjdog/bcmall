package cn.xjjdog.bcmall.module.product.util;

import cn.xjjdog.bcmall.module.product.domain.meta.Brand;
import cn.xjjdog.bcmall.module.product.domain.meta.Category;
import cn.xjjdog.bcmall.module.product.domain.meta.Unit;
import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import cn.xjjdog.bcmall.module.product.domain.product.ProductState;
import cn.xjjdog.bcmall.module.product.domain.product.StockKeepingUnit;
import cn.xjjdog.bcmall.module.product.domain.stock.StockEventType;
import cn.xjjdog.bcmall.module.product.domain.stock.StockHistoryEvent;
import cn.xjjdog.bcmall.module.product.persistence.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface Transform {
    Transform T = Mappers.getMapper(Transform.class);

    Brand fromBrandEntity(BrandEntity v);

    BrandEntity fromBrand(Brand v);

    Unit fromUnitEntity(ProductUnitEntity v);
    default Unit.MeasureType measureTypeIntegerToDomain(Integer value) {
        for (Unit.MeasureType s : Unit.MeasureType.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
    @Mappings({
            @Mapping(source = "measureType.value", target = "measureType")
    })
    ProductUnitEntity fromUnit(Unit v);

    Category fromCategoryEntity(ProductCategoryEntity v);

    ProductCategoryEntity fromCategory(Category v);

    StockHistoryEvent fromStockHistoryEntity(StockHistoryEntity v);

    default StockEventType stockHistoryIntegerToEvent(Integer value) {
        for (StockEventType s : StockEventType.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }

    @Mappings({
            @Mapping(source = "eventType.value", target = "eventType")
    })
    StockHistoryEntity fromStockHistory(StockHistoryEvent v);


    StockKeepingUnit fromSkuEntity(StockKeepingUnitEntity v);

    @Mappings({
            @Mapping(source = "state.value", target = "state")
    })
    StockKeepingUnitEntity fromSku(StockKeepingUnit v);

    StandardProductUnit fromSpuEntity(StandardProductUnitEntity v);

    default ProductState integerToSpuSkuState(Integer value) {
        for (ProductState s : ProductState.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "brand.id", target = "brandId"),
            @Mapping(source = "unit.id", target = "unitId"),
            @Mapping(source = "state.value", target = "state")
    })
    StandardProductUnitEntity fromSpu(StandardProductUnit v);

    List<StockKeepingUnit> fromSkuEntityList(List<StockKeepingUnitEntity> v);

    List<StockKeepingUnitEntity> fromSkuList(List<StockKeepingUnit> v);
}
