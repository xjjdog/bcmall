package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;


/**
 * @author 查询构造器
 */
public interface QueryExecutor {
    /**
     * 通过Query计算当前的数据集
     *
     * @param query
     * @param ctx
     * @return
     * @throws Exception
     */
    ExecutorResult chain(Query query, ExecutorContext ctx) throws InvokeException;

    /**
     * 查询类型，用于和Executor配对
     *
     * @return
     */
    ExecutorType type();
}
