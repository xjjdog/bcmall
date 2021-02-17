package cn.xjjdog.bcmall.module.order.application;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class QuickOrderCommand {
    @Data
    public static final class QuickOrderItem {
        /**
         * SKU ID
         */
        @NotBlank
        private String skuInfo;
        /**
         * 售卖价格
         */
        @Min(0)
        private BigDecimal price;
        /**
         * 售卖数量
         */
        @Min(1)
        private int amount;
    }
    /**
     * 用户信息
     */
    @NotBlank
    private String memberInfo;
    /**
     * Sku信息
     */
    @NotEmpty
    @Valid
    private List<QuickOrderItem> items;

    // 以下是物流信息

    /**
     * 选用的快递公司
     */
    private String expressCompanyId;
    /**
     * 快递单号
     */
    private String expressNumber;
    /**
     * 备注
     */
    private String remark;
    /**
     * 收件人
     */
    @NotEmpty
    private String addressee;
    /**
     * 电话
     */
    @NotEmpty
    private String phone;
    /**
     * 地址
     */
    private String address;
    /**
     * 打折
     */
    @Min(0)
    private BigDecimal discount;
    /**
     * 获得总价格
     * @return
     */
    public BigDecimal getTotalPrice(){
        BigDecimal p = BigDecimal.ZERO;
        for(QuickOrderItem o: getItems()){
            p.add(o.price);
        }
        return p;
    }
}

