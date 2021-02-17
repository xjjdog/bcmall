package cn.xjjdog.bcmall.module.order.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * <p>
 * 物流信息
 */
@Data
@Table(name = "order_logistics")
@Entity
public class LogisticsEntity extends AbstractEntity {
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
     * 物流地址
     */
    private String address;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 物流状态
     */
    private String state;
    /**
     * 明细
     */
    private String details;
}
