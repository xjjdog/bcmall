package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.composite;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.ExecutorType;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.Query;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xjjdog
 */
@Getter
public class CompositeQuery implements Query {
    /**
     * 需要保证查询过程的有序
     */
    private List<Query> childQuerys = new LinkedList<>();

    @Override
    public ExecutorType type() {
        return ExecutorType.COMPOSITE;
    }
}
