package cn.xjjdog.bcmall.module.order.persistence.dao;

import cn.xjjdog.bcmall.module.order.persistence.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface OrderItemDao extends JpaRepository<OrderItemEntity,String> {

}

