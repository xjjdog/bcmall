package cn.xjjdog.bcmall.module.crm.domain;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 会员金融属性集合
 */
@Data
public class MemberBank {
    private Member member;

    /**
     * 余额，充值用
     */
    private int balance;
    /**
     * 积分
     */
    private int point;
    /**
     * 累计消费额
     */
    private int consumerAmount;
}
