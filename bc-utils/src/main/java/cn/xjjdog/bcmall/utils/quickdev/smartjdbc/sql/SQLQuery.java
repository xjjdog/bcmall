package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.ExecutorType;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.Query;
import lombok.Data;

/**
 * @author xjjdog
 * 查询器
 */
@Data
public class SQLQuery implements Query {
    private String dataSourceName;
    private String sql;
    /**
     * 是否分页，仅对SQL查询有效
     */
    private boolean page = false;
    /**
     * 每页条数
     */
    private int psize = 20;

    @Override
    public ExecutorType type() {
        return ExecutorType.SQL;
    }
}
