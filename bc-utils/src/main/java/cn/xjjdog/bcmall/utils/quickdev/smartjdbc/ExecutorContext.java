package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 * 执行的上下文
 */
@Data
public class ExecutorContext {
    private ExecutorContext() {
    }

    /**
     * 输入参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 特殊的类，赋予它一个比较特殊的生成方式
     *
     * @return
     */
    public static ExecutorContext newInstance() {
        return new ExecutorContext();
    }

    /**
     * 执行中的额外的信息载体
     */
    private transient Map<String, Object> addition = new HashMap<>();

    /**
     * 用于每次执行后的结果获取，或者复合类型的执行过程缓存
     */
    private transient List<ContextPair> baggage = new ArrayList<>();

    /**
     * 添加新的结果
     *
     * @param item
     */
    public void addBaggage(ContextPair item) {
        this.baggage.add(item);
    }

    /**
     * 获取某一步的结果
     *
     * @param index
     * @return
     */
    public ContextPair itemAt(int index) {
        return baggage.get(index);
    }

    /**
     * 获取最后一次执行的记录
     *
     * @return
     */
    public ContextPair getLastResult() {
        int index = this.baggage.size() - 1;
        return this.itemAt(index);
    }
}
