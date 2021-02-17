package cn.xjjdog.bcmall.module.product.domain.product;

import cn.xjjdog.bcmall.module.product.domain.meta.Brand;
import cn.xjjdog.bcmall.module.product.domain.meta.Category;
import cn.xjjdog.bcmall.module.product.domain.meta.Unit;
import cn.xjjdog.bcmall.module.product.domain.product.SpecificationRelocateCommand.SpecificationRelocateItem;
import cn.xjjdog.bcmall.utils.BasicInfo;
import cn.xjjdog.bcmall.utils.utils.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 聚合根
 */
@Getter
@Setter
public class StandardProductUnit extends BasicInfo {
    /**
     * 商品简短的名称
     */
    private String shortName;
    /**
     * 商品公共属性
     */
    private String attributes;
    /**
     * 商品状态
     */
    private ProductState state;
    /**
     * 分类信息
     */
    private Category category;
    /**
     * 保存品牌关系
     */
    private Brand brand;
    /**
     * 保存单位关系
     */
    private Unit unit;
    /**
     * 规格配置表
     */
    private String specDefined;
    /**
     * 使用，分割的图像列表
     */
    private String photos;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 依赖的Sku信息
     */
    private List<StockKeepingUnit> skus = Lists.newArrayList();

    private boolean flagHot = false;
    private boolean flagNew = false;
    private boolean flagRecommend = false;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    /**
     * 库存转移
     *
     * @param from
     * @param to
     * @param amount
     */
    public void transfer(String from, String to, int amount) {
        Preconditions.checkArgument(amount > 0, "请填写大于0的数量");
        Preconditions.checkArgument(!from.equals(to), "请选择不同的销售单元进行调整");

        StockKeepingUnit skuFrom = findSkuById(from);
        StockKeepingUnit skuTo = findSkuById(to);

        Preconditions.checkNotNull(skuFrom, "未在 " + getId() + " 中找到 " + from);
        Preconditions.checkNotNull(skuTo, "未在 " + getId() + " 中找到 " + to);

        skuFrom.transfer(skuTo, amount);

        fixState();
    }

    /**
     * 当我们增加，或者删除规格的时候，将会触发SKU的重新分布。
     * <p>
     * 当此方法返回值为true，则证明自动调整完毕，无需额外的手工调整；
     * 当此方法返回值为false，证明需要手工调整。具体调整方式，参见库存调整的（SKU变更库存调整）
     * </p>
     *
     * @return
     * @cmd change request
     */
    public boolean specificationRelocate(SpecificationRelocateCommand cmd) {
        checkSpecificationRelocateCommand(cmd);

        Map<String, Set<String>> specMap = cmd.getSpecMap();
        List<SpecificationRelocateItem> items = cmd.getItems();

        //数据规整
        items.forEach(s -> s.standardized());

        //将数量不同的SKU，标记为不可用，需要调整库存等信息后才能使用
        skus.stream()
                .forEach(s -> s.setState(s.getStorage() == 0
                        ? ProductState.DieOut : ProductState.Dirty)
                );

        List<StockKeepingUnit> skuToAdd = Lists.newArrayList();
        items.forEach(item -> {
            final String key = item.getSpecFlatKey();
            final StockKeepingUnit sku;
            //如果找到了修改目标，那么它们的FlatKey应该是相等的
            StockKeepingUnit forCheck = findSkuByFlatSpecKey(key);

            if (item.isProvide()) {
                //如果提供了ID，就应该找到相应的修改目标
                sku = findSkuById(item.getId());
                sku.setState(ProductState.Normal);

                Preconditions.checkNotNull(sku, "数据错误，未找到ID为 " + item.getId() + "的商品");
                Preconditions.checkArgument(null != forCheck && sku.getId().equals(forCheck.getId()),
                        "数据错误，数据被篡改 " + sku.getId() + "和 " + forCheck.getId() + "不相等");
            } else {
                Preconditions.checkState(null == forCheck, "没有ID，但找到了对应的数据:" + key);

                sku = new StockKeepingUnit();
                sku.setState(ProductState.Normal);
                sku.setStorage(0); // 初始库存

                skuToAdd.add(sku);
            }

            //手动拷贝，避免引入额外技术栈
            sku.setBarcode(item.getBarcode());
            sku.setSpecCode(item.getSpecCode());
            sku.setSpecLabel(item.getSpecLabel());
            sku.setWarnStorage(item.getWarnStorage());
            sku.setKeepStorage(item.getKeepStorage());
            sku.setSpecFlatKey(key);
            sku.setThumbnail(item.getThumbnail());
            sku.setPhotos(item.getPhotos());
            sku.setPrice(item.getPrice());
            sku.setSpuId(getId());
            //ignore storage

            //再次取出并进行验证，此时已经是排序的
            checkSpecKey(sku, specMap.size());
        });


        //追加新的SKU，也就是没有ID的SKU
        skus.addAll(skuToAdd);
        this.setSpecDefinedFromMap(specMap);


        return fixState();
    }

    private boolean fixState() {
        boolean dirty = skus.stream()
                .filter(s -> s.getState() == ProductState.Dirty)
                .count() > 0;

        if (dirty) {
            this.setState(ProductState.Dirty);
        } else {
            if (getState() == ProductState.Dirty) {
                this.setState(ProductState.Normal);
            }
        }
        return dirty;
    }

    /**
     * 一个SPU中的SKU数量是有限的，所以不需要再使用Map缓存一层，直接暴力查找就OK了
     *
     * @param skuId
     * @return
     */
    public StockKeepingUnit findSkuById(String skuId) {
        Preconditions.checkNotNull(skuId, "没有提供ID，请检查数据");
        return skus.stream()
                .filter(s -> s.getId().equals(skuId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据FlatKey（排序好的）查找SKU
     *
     * @param flatSpecKey
     * @return
     */
    public StockKeepingUnit findSkuByFlatSpecKey(String flatSpecKey) {
        String standKey = Try.of(() -> JSON.OM.writeValueAsString(
                Try.of(() -> JSON.OM.readValue(flatSpecKey, TreeMap.class)).getOrElse(new TreeMap()))).getOrElse("");
        return skus.stream()
                .filter(s -> s.getSpecFlatKey().equals(standKey))
                .findFirst()
                .orElse(null);
    }


    /**
     * 将Map类型的信息，转化为String信息，存放在spu中
     *
     * @param specMap
     */
    private void setSpecDefinedFromMap(Map<String, Set<String>> specMap) {
        String defined = Try.of(() -> JSON.OM.writeValueAsString(specMap)).getOrElse("{}");
        this.setSpecDefined(defined);
    }

    /**
     * 参数验证
     *
     * @param cmd
     */
    private void checkSpecificationRelocateCommand(SpecificationRelocateCommand cmd) {
        Map<String, Set<String>> specMap = cmd.getSpecMap();
        List<SpecificationRelocateItem> items = cmd.getItems();

        Preconditions.checkArgument(!CollectionUtils.isEmpty(specMap) && !CollectionUtils.isEmpty(items),
                "数据错误，未提供必要的字段"
        );

        Preconditions.checkArgument(items.size() <= 20, "最多只支持20个商品规格");

        final int exceptSpecSize = specMap.values().stream()
                .map(v -> v.size())
                .reduce(1, (v1, v2) -> v1 * v2);

        Preconditions.checkArgument(exceptSpecSize == items.size(), "规格和规格表数量不一致");
    }

    /**
     * 检查specKey中的元素，是否和期望的一致
     *
     * @param sku
     * @param targetSize
     */
    private void checkSpecKey(StockKeepingUnit sku, int targetSize) {
        Map<String, String> map = sku.fromFlatKey();
        Preconditions.checkArgument(map.size() == targetSize,
                "原始数据错误 :" + targetSize + "| " + sku.getSpecFlatKey());
    }

}

