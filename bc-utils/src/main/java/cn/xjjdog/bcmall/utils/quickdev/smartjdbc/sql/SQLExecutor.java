package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 */
@Component
@Slf4j
public class SQLExecutor implements QueryExecutor {

    public static final String REQ_PARAM_NAME_PAGESIZE = "pageSize";
    public static final String REQ_PARAM_NAME_PAGENUM = "current";


    private String namedPageBegin = "beginAAA";
    private String namedPageEnd = "beginBBB";

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    RagelModeParserSQL parseSQL;

    @Override
    public ExecutorResult chain(Query query, ExecutorContext ctx) throws InvokeException {
        final SQLQuery q = SQLQuery.class.cast(query);
        final String dataSourceName = q.getDataSourceName();

        //获取查询参数
        Map<String, Object> params = ctx.getParams();

        //获取解析后的最终SQL
        String sql = q.getSql();
        sql = parseSQL.getFinalSQL(sql, params);

        Paginate paginate = new Paginate();
        //如果分页，获取总条数
        if (q.isPage()) {
            String countSQL = parseSQL.getCountSQL(sql);

            Integer count = template.queryForObject(countSQL, params, Integer.class);
            if (null == count) {
                count = 0;
            }
            log.debug("final count sql is : {} , with count : {}", countSQL, count);

            Object pnumObj = params.getOrDefault(REQ_PARAM_NAME_PAGENUM, 1);
            Object psizeObj = params.getOrDefault(REQ_PARAM_NAME_PAGESIZE, q.getPsize());
            int pnum = Integer.parseInt(String.valueOf(pnumObj));
            int psize = Integer.parseInt(String.valueOf(psizeObj));

            sql = new StringBuilder(sql)
                    .append(" limit :")
                    .append(namedPageBegin)
                    .append(",:")
                    .append(namedPageEnd)
                    .toString();

            params.put(namedPageBegin, (pnum - 1) * psize);
            params.put(namedPageEnd, psize);

            paginate.setPageNumber(pnum);
            paginate.setTotal(count);
            paginate.setPageSize(psize);
        }

        log.debug("final sql is : {} , with params: {}", sql, params);

        List<Map<String, Object>> obj;
        try {
            obj = template.queryForList(sql, params);
        } catch (Exception ex) {
            throw new InvokeException("query error " + dataSourceName + "/" + ex.getMessage());
        }

        if (q.isPage()) {
            TwoDimensioningResultWithPage result = new TwoDimensioningResultWithPage(obj);
            result.setPaginate(paginate);
            return result;
        } else {
            TwoDimensioningResult result = new TwoDimensioningResult(obj);
            return result;
        }
    }

    @Override
    public ExecutorType type() {
        return ExecutorType.SQL;
    }
}
