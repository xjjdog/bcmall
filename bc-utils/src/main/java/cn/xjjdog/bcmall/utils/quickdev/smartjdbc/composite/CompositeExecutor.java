package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.composite;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xjjdog
 * 复合执行器。类似于管道，上一步的计算结果，将作为下一步的输入。
 * 另外，执行器可以随时通过上下文，得到某一步骤的执行结果
 */
@Component
public class CompositeExecutor implements QueryExecutor {
    @Autowired
    ExecutorStrategy strategy;

    @Override
    public ExecutorResult chain(Query query, ExecutorContext ctx) throws InvokeException {
        CompositeQuery q = CompositeQuery.class.cast(query);
        List<Query> queries = q.getChildQuerys();
        for (Query childQuery : queries) {
            ExecutorResult result = this.strategy.query(childQuery, ctx);
            ContextPair pair = new ContextPair();
            pair.setQuery(query);
            pair.setResult(result);
            ctx.addBaggage(pair);
        }
        return ctx.getLastResult().getResult();
    }

    @Override
    public ExecutorType type() {
        return ExecutorType.COMPOSITE;
    }
}
