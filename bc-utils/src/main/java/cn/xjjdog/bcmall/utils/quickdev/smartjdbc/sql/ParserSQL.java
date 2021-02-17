package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.InvokeException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.Map;

/**
 * @author xjjdog
 */
interface ParserSQL {
    String ENDOFSQL = "\n";

    default String getFinalSQL(String sql, Map<String, Object> params) {

        //SQL处理和判空处理
        sql = sql.trim();
        sql = sql.replaceAll("\\s+", " ");
        sql = sql + ENDOFSQL;

        Map<String, String> transformMap = transform(sql, params);

        for (Map.Entry<String, String> e : transformMap.entrySet()) {
            sql = sql.replace(e.getKey(), e.getValue());
        }
        return sql;
    }

    default String getCountSQL(String sql) throws InvokeException {
        String COUNT_SQL = "SELECT COUNT(*) FROM (%s) CT";

        Select select;
        try {
            select = (Select) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            throw new InvokeException("error while parse final sql " + sql);
        }
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        plainSelect.setOrderByElements(null);
        return String.format(COUNT_SQL, plainSelect.toString());
    }

    Map<String, String> transform(String sql, Map<String, Object> params);

}
