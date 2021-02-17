package cn.xjjdog.bcmall.module.order.controller;

import cn.xjjdog.bcmall.module.order.application.CloseOrderCommand;
import cn.xjjdog.bcmall.module.order.application.OrderService;
import cn.xjjdog.bcmall.module.order.application.QuickOrderCommand;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.SmartJDBC;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.TwoDimensioningResultWithPage;
import cn.xjjdog.bcmall.utils.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    SmartJDBC smartJDBC;
    @Autowired
    OrderService orderService;

    @PostMapping("/createQuickOrder")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Void> createQuickOrder(@Valid @RequestBody QuickOrderCommand quickOrder) {
        orderService.createQuickOrder(quickOrder);
        return Result.of(null);
    }

    @GetMapping("/getOrderList")
    public Result<TwoDimensioningResultWithPage> getOrderList(Integer pageSize,
                                                              Integer current) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("order/getOrderList.sql", params);
        return Result.of(result);
    }

    @PostMapping("/closeOrder")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Void> closeOrder(@Valid @RequestBody CloseOrderCommand cmd) {
        this.orderService.closeOrder(cmd);
        return Result.of(null);
    }
}

