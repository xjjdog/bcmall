package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 */
@Component
@Slf4j
public class ExecutorStrategy implements InitializingBean {
    @Autowired
    private List<QueryExecutor> executors;

    private Map<ExecutorType, QueryExecutor> strategys = new HashMap<>();

    public ExecutorResult query(Query query, ExecutorContext ctx) throws InvokeException {
        final ExecutorType type = query.type();
        final QueryExecutor invoke = this.strategys.get(type);
        return invoke.chain(query, ctx);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (QueryExecutor executor : executors) {
            this.strategys.put(executor.type(), executor);
        }
    }
}
