package cn.xjjdog.bcmall.module.order.util;

import cn.xjjdog.bcmall.module.order.domain.Logistics;
import cn.xjjdog.bcmall.module.order.domain.Order;
import cn.xjjdog.bcmall.module.order.domain.OrderItem;
import cn.xjjdog.bcmall.module.order.domain.OrderState;
import cn.xjjdog.bcmall.module.order.persistence.LogisticsEntity;
import cn.xjjdog.bcmall.module.order.persistence.OrderEntity;
import cn.xjjdog.bcmall.module.order.persistence.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface Transform {
    Transform T = Mappers.getMapper(Transform.class);

    LogisticsEntity fromLogistics(Logistics logistics);

    @Mappings({
            @Mapping(source = "state.value", target = "state")
    })
    OrderEntity fromOrder(Order order);
    Order fromOrderEntity(OrderEntity e);
    OrderItemEntity fromOrderItem(OrderItem item);
    default OrderState integerToOrderState(Integer value) {
        for (OrderState s : OrderState.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }

}
