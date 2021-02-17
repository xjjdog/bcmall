package cn.xjjdog.bcmall.module.order.domain;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 物流信息
 */
@Data
public class Logistics extends BasicInfo {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 快递公司
     */
    private String expressCompanyId;
    /**
     * 快递单号
     */
    private String expressNumber;
    /**
     * 物流状态
     */
    private String state;
    /**
     * 明细
     */
    private String details;
}
