package cn.xjjdog.bcmall.module.crm.controller;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.SmartJDBC;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.TwoDimensioningResult;
import cn.xjjdog.bcmall.utils.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@RestController
@RequestMapping("/api/member")
public class MemberController {


    @Autowired
    SmartJDBC smartJDBC;

    @GetMapping("/memberAutoComplete")
    public Result<TwoDimensioningResult> getSkuListModal(String q) throws Exception {
        Map<String,Object> params = new HashMap<>();
        String qq = Optional.ofNullable(q).orElse("").trim();
        params.put("q",qq);
        TwoDimensioningResult result = smartJDBC.query("crm/memberAutoComplete.sql",params);
        return Result.of(result);
    }
}
