package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import lombok.Data;

/**
 * @author xjjdog
 */
@Data
public class Paginate {
    private int pageNumber = 1;
    private int pageSize = 20;
    private long total = 0;
}
