package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Component
public class SmartJDBC {
    @Autowired
    ExecutorStrategy strategy;

    public TwoDimensioningResultWithPage queryWithPage(String sqlPath,Map<String,Object> params) throws Exception{
        SQLQuery sqlQuery = new SQLQuery();
        sqlQuery.setPage(true);
        sqlQuery.setSql(SmartSQLLoader.loadSQL(sqlPath));
        ExecutorContext ctx = ExecutorContext.newInstance();
        ctx.setParams(params);
        ExecutorResult<Map<String,Object>> result= strategy.query(sqlQuery,ctx);
        return TwoDimensioningResultWithPage.class.cast(result);
    }
    public TwoDimensioningResult query(String sqlPath,Map<String,Object> params) throws Exception{
        SQLQuery sqlQuery = new SQLQuery();
        sqlQuery.setPage(false);
        sqlQuery.setSql(SmartSQLLoader.loadSQL(sqlPath));
        ExecutorContext ctx = ExecutorContext.newInstance();
        ctx.setParams(params);
        ExecutorResult<Map<String,Object>> result= strategy.query(sqlQuery,ctx);
        return TwoDimensioningResult.class.cast(result);
    }
}

