package cn.xjjdog.bcmall.module.order.application;

import cn.xjjdog.bcmall.module.order.domain.Logistics;
import cn.xjjdog.bcmall.module.order.domain.Order;
import cn.xjjdog.bcmall.module.order.domain.OrderItem;
import cn.xjjdog.bcmall.module.order.domain.OrderState;
import cn.xjjdog.bcmall.module.order.persistence.LogisticsEntity;
import cn.xjjdog.bcmall.module.order.persistence.OrderEntity;
import cn.xjjdog.bcmall.module.order.persistence.dao.LogisticsDao;
import cn.xjjdog.bcmall.module.order.persistence.dao.OrderDao;
import cn.xjjdog.bcmall.module.order.persistence.dao.OrderItemDao;
import cn.xjjdog.bcmall.module.order.util.Transform;
import cn.xjjdog.bcmall.module.pay.api.PayApi;
import cn.xjjdog.bcmall.module.product.api.ProductApi;
import cn.xjjdog.bcmall.module.product.application.SingleStorageChangeCommand;
import cn.xjjdog.bcmall.utils.utils.IdUtil;
import cn.xjjdog.bcmall.utils.utils.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final PayApi payApi;
    final ProductApi productApi;

    final LogisticsDao logisticsDao;
    final OrderDao orderDao;
    final OrderItemDao orderItemDao;

    private void createOrder(Order order) {
        OrderEntity orderE = Transform.T.fromOrder(order);
        orderDao.save(orderE);
        for (OrderItem item : order.getItems()) {
            orderItemDao.save(Transform.T.fromOrderItem(item));
        }
    }

    private void createLogistics(Logistics logistics) {
        LogisticsEntity logisticsE = Transform.T.fromLogistics(logistics);
        logisticsDao.save(logisticsE);
    }

    @Override
    public void createQuickOrder(QuickOrderCommand cmd) {
        String memberInfo = cmd.getMemberInfo();
        String memberId = String.valueOf(Try.of(() -> JSON.OM.readValue(memberInfo, Map.class).get("id")).getOrElse(""));

        Preconditions.checkArgument(StringUtils.hasLength(memberId), "未提供正确的会员信息");

        List<QuickOrderCommand.QuickOrderItem> quickOrderItems = cmd.getItems();

        // ID预先生成
        String orderId = IdUtil.genCommonId();

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = Lists.newArrayList();

        //根据sku转化为Order订单明细
        for (QuickOrderCommand.QuickOrderItem p : quickOrderItems) {
            Map skuInfo = Try.of(() -> JSON.OM.readValue(p.getSkuInfo(), Map.class)).getOrElse(Collections.EMPTY_MAP);
            Object skuId = skuInfo.getOrDefault("id", null);
            Object spuId = skuInfo.getOrDefault("spuId", null);
            Preconditions.checkNotNull(skuId, "参数错误, skuId为空");
            Preconditions.checkNotNull(spuId, "参数错误，spuId为空");

            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(String.valueOf(skuId));
            orderItem.setSpuId(String.valueOf(spuId));
            orderItem.setSkuInfo(p.getSkuInfo());
            orderItem.setPrice(p.getPrice());
            orderItem.setAmount(p.getAmount());
            orderItem.setRealPrice(p.getPrice().multiply(new BigDecimal(p.getAmount())));
            orderItem.setOrderId(orderId);

            totalPrice = totalPrice.add(orderItem.getRealPrice());

            orderItems.add(orderItem);
        }

        totalPrice = totalPrice.subtract(cmd.getDiscount());


        //创建一个快速订单
        Order order = new Order();
        order.setState(OrderState.Delivery); //直接进入发货阶段
        order.setId(orderId);
        order.setItems(orderItems);
        order.setSkusInfo(Try.of(() -> JSON.OM.writeValueAsString(cmd.getItems())).getOrElse("{}"));
        order.setTotalPrice(totalPrice);
        order.setDiscount(cmd.getDiscount());

        order.setMemberId(memberId);
        order.setMemberInfo(memberInfo);

        Logistics logistics = new Logistics();
        logistics.setOrderId(orderId);
        logistics.setExpressCompanyId(cmd.getExpressCompanyId());
        logistics.setExpressNumber(cmd.getExpressNumber());
        order.setLogistics(logistics);


        this.createOrder(order);
        this.createLogistics(logistics);

        for(OrderItem orderItem : orderItems){
            SingleStorageChangeCommand request = new SingleStorageChangeCommand();
            request.setSkuId(orderItem.getSkuId());
            request.setAmount(orderItem.getAmount());
            request.setPrice(orderItem.getPrice());
            request.setReason(orderId);
            productApi.stockOut(request);
        }
    }

    @Override
    @Transactional
    public void closeOrder(CloseOrderCommand cmd) {
        final String orderId = cmd.getOrderId();
        final OrderState state = cmd.isNormal() ? OrderState.Closed : OrderState.FalseClosed;
        OrderEntity orderEntity = orderDao.findById(orderId).orElse(null);
        Order order = Transform.T.fromOrderEntity(orderEntity);
        order.setRemark(order.getRemark() + cmd.getRemark());
        order.setState(state);

        final OrderEntity saveEntity = Transform.T.fromOrder(order);
        this.orderDao.saveAndFlush(saveEntity);
    }
}

