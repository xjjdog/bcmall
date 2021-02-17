package cn.xjjdog.bcmall.module.product.domain.product;

import cn.xjjdog.bcmall.utils.utils.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.vavr.control.Try;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 库存调整命令
 */
@Data
public class SpecificationRelocateCommand {
    /**
     * 调整目标配置
     * 数据样例 : {"color":["red","blue"],"size":["大","小"]}
     */
    private Map<String, Set<String>> specMap;
    /**
     * 调整配置明细表
     */
    private List<SpecificationRelocateItem> items;

    /**
     * 属性拷贝自SKU，属性名称保持一致
     *
     * @see StockKeepingUnit
     */
    @Data
    public static class SpecificationRelocateItem {
        private String id;
        private String specFlatKey;
        /**
         * SKU
         */
        private String specCode;
        private int storage;
        private BigDecimal price;
        private String barcode;
        private String specLabel;
        private String photos;
        private String thumbnail;

        private int keepStorage;
        private int warnStorage;

        /**
         * @return 对象是否已经存在
         */
        public boolean isProvide(){
            return !Strings.isNullOrEmpty(id);
        }

        /**
         * 数据规整，经过TreeMap处理后的Key，可以根据自然顺序排序
         */
       public void standardized(){
           String sortedValue = Try.of(()->
                   JSON.OM.writeValueAsString(
                           JSON.OM.readValue(getSpecFlatKey(), TreeMap.class)
                   )
           ).getOrElse("");
           Preconditions.checkArgument(!Strings.isNullOrEmpty(sortedValue),"提供的数据有误:" + getSpecFlatKey());
           //此时的值已经排序，可以和SKU中的值进行精确对比
           setSpecFlatKey(sortedValue);
       }
    }

}
